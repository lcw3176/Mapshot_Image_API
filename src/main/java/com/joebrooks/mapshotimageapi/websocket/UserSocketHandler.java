package com.joebrooks.mapshotimageapi.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSocketHandler extends TextWebSocketHandler {

    private final TaskService taskService;
    private final SlackClient slackClient;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        taskService.addSession(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        UserMapRequest request;

        try{
            request = mapper.readValue(message.getPayload(), UserMapRequest.class);
        } catch (JsonProcessingException e){
            log.error("유효하지 않은 지도 포맷", e);
            slackClient.sendMessage("유효하지 않은 지도 포맷", e);
            taskService.removeSession(session);

            return;
        }

        taskService.sendWaitersCountToUser(session);
        taskService.execute(request, session).thenAccept(result -> {
            if(session.isOpen()){
                try {
                    session.sendMessage(new TextMessage(mapper.writeValueAsString(result)));
                } catch (Exception e) {
                    log.error("대기열 전송 실패", e);
                    slackClient.sendMessage("대기열 전송 실패", e);
                }
            } else {
                taskService.popImage(result.getUuid());
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        taskService.removeSession(session);
        taskService.sendLeftCountToWaiters();
    }

}