package com.joebrooks.mapshotimageapi.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.processing.Processing;
import com.joebrooks.mapshotimageapi.processing.ProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final SlackClient slackClient;
    private final ObjectMapper mapper;
    private final List<WebSocketSession> sessionList = Collections.synchronizedList(new LinkedList<>());
    private final ProcessingService processingService;

    public void closeSession(WebSocketSession session){
        sessionList.remove(session);
    }

    public void broadcastWaiterCounts(){
        for (WebSocketSession sessions : sessionList) {
            sendWaitInfoMessage(sessions);
        }
    }

    public void noticeWaitNumber(WebSocketSession session){
        sendWaitInfoMessage(session);
    }


    public void onReceive(WebSocketSession session, TextMessage message){

        try {
            UserRequest request = mapper.readValue(message.getPayload(), UserRequest.class);

            sessionList.add(session);
            noticeWaitNumber(session);

            processingService.addTask(Processing.builder()
                    .request(request)
                    .session(session)
                    .build());

        } catch (JsonProcessingException e) {
            log.error("유효하지 않은 지도 포맷", e);
            slackClient.sendMessage(e);

        }
    }


    public void onClose(WebSocketSession session){
        closeSession(session);
        broadcastWaiterCounts();
    }




    // 이미지 정보 보내주기
    public boolean sendInfo(UserResponse userResponse, WebSocketSession session) {
        if(!session.isOpen()){
            return false;
        }

        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(userResponse)));
            return true;
        } catch (IOException e){
            log.error("메세지 전송 에러", e);
            slackClient.sendMessage(e);

            return false;
        }

    }



    private void sendWaitInfoMessage(WebSocketSession session){

        if(!session.isOpen()){
            sessionList.remove(session);
            return;
        }

        UserResponse refreshedResponse = UserResponse.builder()
                .index(sessionList.indexOf(session))
                .build();

        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(refreshedResponse)));
        } catch (IOException e){
            log.error("대기열 알람 전송 에러", e);
            slackClient.sendMessage(e);
        }
    }
}
