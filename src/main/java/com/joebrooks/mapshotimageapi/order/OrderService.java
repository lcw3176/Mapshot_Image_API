package com.joebrooks.mapshotimageapi.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.global.IDataReceiver;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.processing.Processing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final SlackClient slackClient;
    private final ObjectMapper mapper;

    private final IDataReceiver processingReceiver;

    private final List<WebSocketSession> userSessionList = Collections.synchronizedList(new LinkedList<>());

    public void closeSession(WebSocketSession session){
        userSessionList.remove(session);
    }

    public void broadcastWaiterCounts(){
        for (WebSocketSession session : userSessionList) {
            sendWaitInfoMessage(session);
        }
    }

    public void noticeWaitNumber(WebSocketSession session){
        sendWaitInfoMessage(session);
    }


    public void onReceive(WebSocketSession session, TextMessage message){

        try {
            Order order = mapper.readValue(message.getPayload(), Order.class);

            userSessionList.add(session);
            noticeWaitNumber(session);

            processingReceiver.receive(Processing.builder()
                    .requestUri(OrderUtil.getUri(order))
                    .mapWidth(OrderUtil.getWidth(order))
                    .session(session)
                    .build());

        } catch (JsonProcessingException e) {
            log.error("유효하지 않은 지도 포맷", e);
            slackClient.sendMessage(e);

        } catch (Exception e){
            log.error(e.getMessage(), e);
            slackClient.sendMessage(e);
        }
    }


    public void onClose(WebSocketSession session){
        closeSession(session);
        broadcastWaiterCounts();
    }




    private void sendWaitInfoMessage(WebSocketSession session){

        if(!session.isOpen()){
            userSessionList.remove(session);
            return;
        }

        OrderResponse refreshedResponse = OrderResponse.builder()
                .index(userSessionList.indexOf(session))
                .build();

        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(refreshedResponse)));
        } catch (IOException e){
            log.error("대기열 알람 전송 에러", e);
            slackClient.sendMessage(e);
        }
    }
}
