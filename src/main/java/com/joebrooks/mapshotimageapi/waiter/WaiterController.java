package com.joebrooks.mapshotimageapi.waiter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WaiterController {

    private final WaiterService waiterService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/waiter")
    @SendTo("/queue/waiter")
    public Waiter addWaiters(@Header("simpSessionId") String sessionId){
        int position = waiterService.getWaiterPosition(sessionId);

        return Waiter.builder()
                .index(position)
                .sessionId(sessionId)
                .build();
    }

    @EventListener
    public void noticeWaitCountToUsers(Waiter waiter){
        messagingTemplate.convertAndSend("/queue/waiter", waiter);
    }


}
