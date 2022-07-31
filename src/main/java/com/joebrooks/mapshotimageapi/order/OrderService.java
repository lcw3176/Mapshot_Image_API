package com.joebrooks.mapshotimageapi.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.factory.FactoryService;
import com.joebrooks.mapshotimageapi.factory.FactoryTask;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
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
public class OrderService {

    private final SlackClient slackClient;
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<WebSocketSession> sessionList = Collections.synchronizedList(new LinkedList<>());
    private final FactoryService factoryService;

    public void onProgress(WebSocketSession session, TextMessage message){

        try {
            UserMapRequest request = mapper.readValue(message.getPayload(), UserMapRequest.class);

            sessionList.add(session);
            sendWaitersCount(session);

            factoryService.addTask(FactoryTask.builder()
                    .requestUri(request.getUri())
                    .width(request.getWidth())
                    .session(session)
                    .build());

        } catch (JsonProcessingException e) {
            log.error("유효하지 않은 지도 포맷", e);
            slackClient.sendMessage(e);

        }
    }


    public void onClose(WebSocketSession session){
        sessionList.remove(session);
        sendWaitersCount();
    }

    // 이미지 정보 보내주기
    public void onComplete(ResponseInfo responseInfo) throws IOException {
        responseInfo.getSession().sendMessage(new TextMessage(
                mapper.writeValueAsString(UserMapResponse.builder()
                        .index(responseInfo.getIndex())
                        .x(responseInfo.getX())
                        .y(responseInfo.getY())
                        .error(responseInfo.isError())
                        .uuid(responseInfo.getUuid())
                        .build())));
    }



    // 단일 유저에게 현재 대기 인원 보내기
    private void sendWaitersCount(WebSocketSession session) {
        sendWaitInfoMessage(session);
    }

    // 전체 유저에게 현재 대기 인원 보내기
    private void sendWaitersCount() {
        for (WebSocketSession session : sessionList) {
            sendWaitInfoMessage(session);
        }
    }

    private void sendWaitInfoMessage(WebSocketSession session){

        if(!session.isOpen()){
            sessionList.remove(session);
            return;
        }

        UserMapResponse refreshedResponse = UserMapResponse.builder()
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
