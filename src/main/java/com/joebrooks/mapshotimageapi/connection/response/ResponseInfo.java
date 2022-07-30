package com.joebrooks.mapshotimageapi.connection.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseInfo {

    private int index;
    private int x;
    private int y;
    private String uuid;
    private boolean error;
    private WebSocketSession session;
    private COMMAND command;

    public enum COMMAND{
        SEND,
        CLOSE
    }
}
