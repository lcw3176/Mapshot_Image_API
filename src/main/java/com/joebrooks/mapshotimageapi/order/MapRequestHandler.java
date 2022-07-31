package com.joebrooks.mapshotimageapi.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class MapRequestHandler extends TextWebSocketHandler {


    private final OrderService orderService;


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        orderService.onClose(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        orderService.onClose(session);
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        orderService.onProgress(session, message);
    }

}
