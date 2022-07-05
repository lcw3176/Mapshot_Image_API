package com.joebrooks.mapshotimageapi.factory;

import com.joebrooks.mapshotimageapi.global.model.UserMapRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FactoryTask {

    private UserMapRequest userMapRequest;
    private WebSocketSession session;
}
