package com.joebrooks.mapshotimageapi.waiter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Service
@RequiredArgsConstructor
public class WaiterService {

    private final List<String> userSessionList = Collections.synchronizedList(new ArrayList<>());
    private final ApplicationEventPublisher eventPublisher;
    
    public int getWaiterPosition(String sessionId){
        return userSessionList.indexOf(sessionId);
    }


    @EventListener
    private void onSessionConnectedEvent(SessionConnectedEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        userSessionList.add(sha.getSessionId());
    }

    @EventListener
    private void onSessionDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        userSessionList.remove(sha.getSessionId());

        for(int i = 0; i < userSessionList.size(); i++){
            eventPublisher.publishEvent(Waiter.builder()
                    .sessionId(userSessionList.get(i))
                    .index(i)
                    .build());
        }
    }

}
