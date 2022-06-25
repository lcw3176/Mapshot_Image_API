package com.joebrooks.mapshotimageapi.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.factory.UserMapRequest;
import com.joebrooks.mapshotimageapi.factory.FactoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager webSocketSessionManager;
    private final FactoryService factoryService;
    private final SlackClient slackClient;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        webSocketSessionManager.addSession(session);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        UserMapRequest request;

        try{
            request = mapper.readValue(message.getPayload(), UserMapRequest.class);
        } catch (JsonProcessingException e){
            log.error("유효하지 않은 지도 포맷", e);
            slackClient.sendMessage("유효하지 않은 지도 포맷", e);
            webSocketSessionManager.removeSession(session);
            return;
        }

        webSocketSessionManager.sendWaitersCount(session);
        factoryService.execute(request, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSessionManager.removeSession(session);
        webSocketSessionManager.sendWaitersCount();
    }

}