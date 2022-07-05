package com.joebrooks.mapshotimageapi.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.global.model.UserMapResponse;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Component
public class UserMapResponseListener {

    private final ObjectMapper mapper = new ObjectMapper();

    // 유저에게 지도 정보 보내주기
    @EventListener
    public void sendMapImageInfo(UserMapResponse userMapResponse) throws IOException {
        userMapResponse.getSession().sendMessage(new TextMessage(mapper.writeValueAsString(userMapResponse)));
    }

}
