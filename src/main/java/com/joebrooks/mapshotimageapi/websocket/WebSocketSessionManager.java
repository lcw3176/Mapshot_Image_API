package com.joebrooks.mapshotimageapi.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.global.model.UserMapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedList;

/*
웹소켓 세션을 관리하는 클래스입니다.
세션 추가, 삭제, 대기하는 유저들에게 현재 자신이 몇 번째 순번인지
알려주고 있습니다.
*/
@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketSessionManager {

    private final static LinkedList<WebSocketSession> sessionList = new LinkedList<>();
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
        sendMessage(session);
    }

    // 전체 유저에게 현재 대기 인원 보내기
    public void sendWaitersCount() {
        for (WebSocketSession session : sessionList) {
            sendMessage(session);
        }
    }

    private void sendMessage(WebSocketSession session){
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
