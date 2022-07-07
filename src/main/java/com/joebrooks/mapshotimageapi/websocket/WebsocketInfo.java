package com.joebrooks.mapshotimageapi.websocket;

import com.joebrooks.mapshotimageapi.global.model.UserMapResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebsocketInfo {

    private UserMapResponse userMapResponse;
    private WebSocketSession session;
}
