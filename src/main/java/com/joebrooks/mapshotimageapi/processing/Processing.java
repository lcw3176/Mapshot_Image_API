package com.joebrooks.mapshotimageapi.processing;

import com.joebrooks.mapshotimageapi.global.IData;
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
public class Processing implements IData {
    private UriComponents requestUri;
    private int mapWidth;
    private WebSocketSession session;

}
