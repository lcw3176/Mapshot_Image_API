package com.joebrooks.mapshotimageapi.task;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final SimpMessagingTemplate messagingTemplate;
    private final TaskService taskService;

    @MessageMapping("/task")
    public void addUserTaskToQueue(@Payload TaskRequest request, @Header("simpSessionId") String sessionId){
        request.setSessionId(sessionId);
        taskService.addTask(request);
    }


    @EventListener
    public void sendImageInfoToUser(TaskResponse response){
        messagingTemplate.convertAndSend("/queue/task/" + response.getSessionId(), response);
    }
}
