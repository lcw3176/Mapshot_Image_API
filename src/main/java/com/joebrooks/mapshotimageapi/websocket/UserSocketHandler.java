package com.joebrooks.mapshotimageapi.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.global.model.UserMapRequest;
import com.joebrooks.mapshotimageapi.factory.FactoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionService webSocketSessionService;
    private final FactoryService factoryService;
    private final SlackClient slackClient;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        webSocketSessionService.addUser(session);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        UserMapRequest request;

        try{
            request = mapper.readValue(message.getPayload(), UserMapRequest.class);
        } catch (JsonProcessingException e){
            log.error("유효하지 않은 지도 포맷", e);
            slackClient.sendMessage(e);
            webSocketSessionService.removeUser(session);
            return;
        }

        // 현재 유저가 몇 번째 대기유저인지 보내준 후, 작업 시작
        webSocketSessionService.sendWaitersCount(session);
        factoryService.addTask(request, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSessionService.removeUser(session);
        webSocketSessionService.sendWaitersCount();
    }

}