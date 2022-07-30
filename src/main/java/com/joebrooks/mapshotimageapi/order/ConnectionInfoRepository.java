package com.joebrooks.mapshotimageapi.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.socket.WebSocketSession;

public interface ConnectionInfoRepository extends CrudRepository<ConnectionInfo, String> {
    boolean existsByWebSocketSession(WebSocketSession session);
}
