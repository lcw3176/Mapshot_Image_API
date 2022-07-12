package com.joebrooks.mapshotimageapi.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.global.memorydb.IMemoryDB;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/*
웹소켓 세션을 관리하는 클래스입니다.
세션 추가, 삭제, 대기하는 유저들에게 현재 자신이 몇 번째 순번인지
알려주고 있습니다.
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketSessionService {

    private final SlackClient slackClient;
    private final ObjectMapper mapper = new ObjectMapper();
    private final IMemoryDB<WebSocketSession> sessionMemoryDB;

    public void addUser(WebSocketSession session){
        if(!sessionMemoryDB.contains(session)){
            sessionMemoryDB.add(session);
        }
    }

    private void removeUser(WebSocketSession session){
        sessionMemoryDB.remove(session);
    }

    // 이미지 정보 보내주기
    public void sendData(WebsocketInfo websocketInfo) throws IOException {
        websocketInfo.getSession().sendMessage(new TextMessage(
                mapper.writeValueAsString(UserMapResponse.builder()
                                .index(websocketInfo.getIndex())
                                .x(websocketInfo.getX())
                                .y(websocketInfo.getY())
                                .uuid(websocketInfo.getUuid())
                                .build())));
    }

    public void onClose(WebSocketSession session){
        removeUser(session);
        sendWaitersCount();
    }


    // 단일 유저에게 현재 대기 인원 보내기
    public void sendWaitersCount(WebSocketSession session) {
        sendWaitInfoMessage(session);
    }

    // 전체 유저에게 현재 대기 인원 보내기
    private void sendWaitersCount() {
        for (WebSocketSession session : sessionMemoryDB.getAll()) {
            sendWaitInfoMessage(session);
        }
    }

    private void sendWaitInfoMessage(WebSocketSession session){

        if(!session.isOpen()){
            removeUser(session);
            return;
        }

        UserMapResponse refreshedResponse = UserMapResponse.builder()
                .index(sessionMemoryDB.getAll().indexOf(session))
                .build();

        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(refreshedResponse)));
        } catch (IOException e){
            log.error("대기열 알람 전송 에러", e);
            slackClient.sendMessage(e);
        }
    }
}
