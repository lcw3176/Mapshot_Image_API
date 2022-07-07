package com.joebrooks.mapshotimageapi.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class WebsocketInfoListener {

    private final WebSocketSessionService sessionService;

    // 유저에게 지도 정보 보내주기
    @EventListener
    public void sendMapImageInfo(WebsocketInfo websocketInfo) throws IOException {

        switch (websocketInfo.getCommand()){
            case SEND:
                sessionService.sendMessage(websocketInfo);
                break;

            case CLOSE:
                sessionService.removeUser(websocketInfo.getSession());
                sessionService.sendWaitersCount();
                break;

            default:
                break;
        }
    }

}
