package com.joebrooks.mapshotimageapi.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.factory.FactoryTask;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@Slf4j
public class MapRequestHandler extends AbstractMapRequestHandler {

    private final ApplicationEventPublisher eventPublisher;
    private final SlackClient slackClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public MapRequestHandler(SessionHandler sessionHandler,
                             ApplicationEventPublisher eventPublisher,
                             SlackClient slackClient) {
        super(sessionHandler);

        this.eventPublisher = eventPublisher;
        this.slackClient = slackClient;
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        UserMapRequest request;

        try{
            request = mapper.readValue(message.getPayload(), UserMapRequest.class);
        } catch (JsonProcessingException e){
            log.error("유효하지 않은 지도 포맷", e);
            slackClient.sendMessage(e);
            sessionHandler.onClose(session);

            return;
        }

        // 현재 유저가 몇 번째 대기유저인지 보내준 후, 작업 시작
        sessionHandler.onProgress(session);

        eventPublisher.publishEvent(FactoryTask.builder()
                .requestUri(request.getUri())
                .width(request.getWidth())
                .session(session)
                .build());
    }

}
