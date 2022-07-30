package com.joebrooks.mapshotimageapi.order.request;

import com.joebrooks.mapshotimageapi.order.response.ResponseInfo;
import com.joebrooks.mapshotimageapi.order.response.SessionHandler;
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
    public void listen(ResponseInfo responseInfo) throws IOException {

        switch (responseInfo.getCommand()){
            case SEND:
                sessionHandler.onComplete(responseInfo);
                break;

            case CLOSE:
                sessionHandler.onClose(responseInfo.getSession());
                break;

            default:
                break;
        }
    }

}
