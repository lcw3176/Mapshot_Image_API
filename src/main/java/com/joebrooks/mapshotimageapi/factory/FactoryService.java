package com.joebrooks.mapshotimageapi.factory;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.driver.DriverService;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.global.util.UriGenerator;
import com.joebrooks.mapshotimageapi.global.util.WidthExtractor;
import com.joebrooks.mapshotimageapi.storage.StorageManager;
import com.joebrooks.mapshotimageapi.websocket.WebSocketSessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

/*
알맞은 좌표의 위성 이미지를 요청한 후 이미지를 크롤링합니다.
한 번에 하나의 이미지 작업만 수행하며, 이미지를 분할해서 캡쳐한 후
해당 이미지가 전체 이미지 중 어느 위치에서 캡쳐된 이미지인지 나타내주는 x,y 좌표값과
uuid 값을 유저에게 웹소켓으로 전송해줍니다.
*/

@Service
@RequiredArgsConstructor
@Slf4j
public class FactoryService {

    private final StorageManager storageManager;
    private final DriverService driverService;
    private final SlackClient slackClient;
    private final WebSocketSessionManager webSocketSessionManager;
    private final ObjectMapper mapper = new ObjectMapper();


    @Async
    public void execute(UserMapRequest request, WebSocketSession session){

        if(!session.isOpen()){
            webSocketSessionManager.removeSession(session);
            return;
        }

        try{
            driverService.loadPage(UriGenerator.getUri(request));
            int width = WidthExtractor.extract(request);

            for(int y = 0; y < width; y+= 1000){
                for(int x = 0; x < width; x+= 1000){
                    try {
                        driverService.scrollPage(x, y);
                        ByteArrayResource byteArrayResource = driverService.capturePage();

                        // 세션이 열려있다면 이미지 uuid 전송, 닫혔다면 작업 정지
                        if(session.isOpen()){
                            String uuid = UUID.randomUUID().toString();
                            storageManager.add(uuid, byteArrayResource);

                            UserMapResponse response = UserMapResponse.builder()
                                    .index(0)
                                    .x(x)
                                    .y(y)
                                    .uuid(uuid)
                                    .build();

                            session.sendMessage(new TextMessage(mapper.writeValueAsString(response)));
                        } else {
                            return;
                        }

                    } catch (Exception e){
                        log.error(e.getMessage(), e);
                        slackClient.sendMessage(e.getMessage(), e);
                    }
                }
            }


        } catch (Exception e){
            log.error(e.getMessage(), e);
            slackClient.sendMessage(e.getMessage(), e);
        }

    }

}