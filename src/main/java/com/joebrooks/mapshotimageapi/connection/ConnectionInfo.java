package com.joebrooks.mapshotimageapi.connection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.web.socket.WebSocketSession;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("connection-info")
public class ConnectionInfo {

    @Id
    private String id;
    private WebSocketSession webSocketSession;
}
