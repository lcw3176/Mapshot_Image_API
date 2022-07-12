package com.joebrooks.mapshotimageapi.factory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.UriComponents;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FactoryTask {

    private UriComponents requestUri;
    private int width;
    private WebSocketSession session;
}
