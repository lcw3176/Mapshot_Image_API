package com.joebrooks.mapshotimageapi.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;


/*
웹소켓 세션을 관리하는 클래스입니다.
세션 추가, 삭제, 대기하는 유저들에게 현재 자신이 몇 번째 순번인지
알려주고 있습니다.
*/
@Component
@RequiredArgsConstructor
@Slf4j
public class SessionHandler {

    private final SlackClient slackClient;
    private final ObjectMapper mapper = new ObjectMapper();
    private final WebSocketSessionService webSocketSessionService;

    public void onConnect(WebSocketSession session){
        webSocketSessionService.addUser(session);
    }

    public void onProgress(WebSocketSession session){
        sendWaitersCount(session);
    }


    public void onClose(WebSocketSession session){
        webSocketSessionService.removeUser(session);
        sendWaitersCount();
    }

    // 이미지 정보 보내주기
    public void onComplete(WebsocketInfo websocketInfo) throws IOException {
        websocketInfo.getSession().sendMessage(new TextMessage(
                mapper.writeValueAsString(UserMapResponse.builder()
                        .index(websocketInfo.getIndex())
                        .x(websocketInfo.getX())
                        .y(websocketInfo.getY())
                        .error(websocketInfo.isError())
                        .uuid(websocketInfo.getUuid())
                        .build())));
    }



    // 단일 유저에게 현재 대기 인원 보내기
    private void sendWaitersCount(WebSocketSession session) {
        sendWaitInfoMessage(session);
    }

    // 전체 유저에게 현재 대기 인원 보내기
    private void sendWaitersCount() {
        for (WebSocketSession session : webSocketSessionService.getAllUsers()) {
            sendWaitInfoMessage(session);
        }
    }

    private void sendWaitInfoMessage(WebSocketSession session){

        if(!session.isOpen()){
            webSocketSessionService.removeUser(session);
            return;
        }

        UserMapResponse refreshedResponse = UserMapResponse.builder()
                .index(webSocketSessionService.getAllUsers().indexOf(session))
                .build();

        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(refreshedResponse)));
        } catch (IOException e){
            log.error("대기열 알람 전송 에러", e);
            slackClient.sendMessage(e);
        }
    }
}
