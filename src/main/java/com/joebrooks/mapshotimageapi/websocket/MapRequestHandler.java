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
            return;
        }
        
        // 봇으로 추정되는 애들이 소켓 커넥션만 해서 대기열 관리가 이상해진다
        // 유저가 지도 요청을 보냈을 때에만 DB에 추가
        sessionHandler.onConnect(session);

        // 현재 유저가 몇 번째 대기유저인지 보내준 후, 작업 시작
        sessionHandler.onProgress(session);

        eventPublisher.publishEvent(FactoryTask.builder()
                .requestUri(request.getUri())
                .width(request.getWidth())
                .session(session)
                .build());
    }

}
