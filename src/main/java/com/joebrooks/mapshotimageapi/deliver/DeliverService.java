package com.joebrooks.mapshotimageapi.deliver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.global.IDataReceiver;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliverService {

    private final ObjectMapper mapper;
    private final SlackClient slackClient;
    private final IDataReceiver storageReceiver;

    //todo 더 깔끔한 방법 구상좀 해보자
    public Delivery setup(Delivery delivery){
        if(delivery.isError()){
            storageReceiver.receive(Storage.builder()
                    .error(delivery.isError())
                    .build());

            return delivery;
        }

        String uuid = UUID.randomUUID().toString();

        storageReceiver.receive(Storage.builder()
                .uuid(uuid)
                .imageByte(delivery.getImageByte())
                .error(delivery.isError())
                .createdAt(LocalDateTime.now())
                .build());

        delivery.setUuid(uuid);

        return delivery;
    }


    // 이미지 정보 보내주기
    public boolean sendInfo(Delivery delivery) {
        if(!delivery.getSession().isOpen()){
            return false;
        }

        try{
            delivery.getSession().sendMessage(new TextMessage(mapper.writeValueAsString(DeliveryResponse.builder()
                    .error(delivery.isError())
                    .index(delivery.getIndex())
                    .x(delivery.getX())
                    .y(delivery.getY())
                    .uuid(delivery.getUuid())
                    .build())));

            return true;
        } catch (IOException e){
            log.error("메세지 전송 에러", e);
            slackClient.sendMessage(e);

            return false;
        }

    }
}
