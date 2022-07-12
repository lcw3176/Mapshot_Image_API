package com.joebrooks.mapshotimageapi.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class WebsocketInfoListener {

    private final SessionHandler sessionHandler;

    // 유저에게 지도 정보 보내주기
    @EventListener
    public void listen(WebsocketInfo websocketInfo) throws IOException {

        switch (websocketInfo.getCommand()){
            case SEND:
                sessionHandler.onComplete(websocketInfo);
                break;

            case CLOSE:
                sessionHandler.onClose(websocketInfo.getSession());
                break;

            default:
                break;
        }
    }

}
