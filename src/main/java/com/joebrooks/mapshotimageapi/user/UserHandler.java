package com.joebrooks.mapshotimageapi.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class UserHandler extends TextWebSocketHandler {


    private final UserService userService;


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        userService.onClose(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        userService.onClose(session);
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        userService.onReceive(session, message);
    }

}

