package com.joebrooks.mapshotimageapi.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.map.UserMapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedList;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketSessionManager {

    private final LinkedList<WebSocketSession> sessionList = new LinkedList<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final SlackClient slackClient;

    public void addSession(WebSocketSession session){
        sessionList.add(session);
    }

    public void removeSession(WebSocketSession session){
        sessionList.remove(session);
    }
    
    // 단일 유저에게 현재 대기 인원 보내기
    public void sendWaitersCount(WebSocketSession session) {
        UserMapResponse refreshedResponse = UserMapResponse.builder()
                .index(sessionList.indexOf(session))
                .build();

        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(refreshedResponse)));
        } catch (IOException e){
            log.error("대기열 알람 전송 에러", e);
            slackClient.sendMessage("대기열 알람 전송 에러", e);
        }

    }

    // 전체 유저에게 현재 대기 인원 보내기
    public void sendWaitersCount() {
        for(int i = 0; i < sessionList.size(); i++){
            UserMapResponse refreshedResponse = UserMapResponse.builder()
                    .index(sessionList.indexOf(sessionList.get(i)))
                    .build();

            try{
                sessionList.get(i).sendMessage(new TextMessage(mapper.writeValueAsString(refreshedResponse)));
            } catch (IOException e){
                log.error("대기열 알람 전송 에러", e);
                slackClient.sendMessage("대기열 알람 전송 에러", e);
            }

        }
    }
}
