package com.joebrooks.mapshotimageapi.deliver;

import com.joebrooks.mapshotimageapi.global.IData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery implements IData {


    private int index;
    private int x;
    private int y;
    private String uuid;
    private boolean error;
    private WebSocketSession session;
    private byte[] imageByte;

    public void setUuid(String uuid){
        this.uuid = uuid;
    }
}
