package com.joebrooks.mapshotimageapi.task;


import com.joebrooks.mapshotimageapi.driver.DriverService;
import com.joebrooks.mapshotimageapi.driver.LoadMapException;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.global.util.UriGenerator;
import com.joebrooks.mapshotimageapi.global.util.WidthExtractor;
import com.joebrooks.mapshotimageapi.map.UserMapRequest;
import com.joebrooks.mapshotimageapi.map.UserMapResponse;
import com.joebrooks.mapshotimageapi.websocket.WebSocketSessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final static Map<String, ByteArrayResource> imageMap = new HashMap<>();
    private final DriverService driverService;
    private final SlackClient slackClient;
    private final WebSocketSessionManager webSocketSessionManager;


//    @Async
//    public CompletableFuture<UserMapResponse> execute(UserMapRequest request, WebSocketSession session){
//
//        if(!session.isOpen()){
//            webSocketSessionManager.removeSession(session);
//            return CompletableFuture.completedFuture(null);
//        }
//
//        try{
//            driverService.loadPage(UriGenerator.getUri(request));
//        } catch (Exception e){
//            log.error("지도 로딩 에러", e);
//            slackClient.sendMessage("지도 로딩 에러", e);
//        }
//
//
//        UserMapResponse response;
//        ByteArrayResource byteArrayResource = null;
//        try {
//            byteArrayResource = driverService.capturePage();
//        } catch (Exception e){
//            log.error("지도 캡쳐 에러", e);
//            slackClient.sendMessage("지도 캡쳐 에러", e);
//        } finally {
//            String uuid = UUID.randomUUID().toString();
//            imageMap.put(uuid, byteArrayResource);
//            response = UserMapResponse.builder()
//                    .done(true)
//                    .index(0)
//                    .uuid(uuid)
//                    .build();
//        }
//
//        return CompletableFuture.completedFuture(response);
//    }

    @Async
    public CompletableFuture<Void> execute(UserMapRequest request, WebSocketSession session){

        if(!session.isOpen()){
            webSocketSessionManager.removeSession(session);
            return new CompletableFuture<Void>();
        }

        try{
            driverService.loadPage(UriGenerator.getUri(request));
            int width = WidthExtractor.extract(request);

        } catch (NoSuchElementException e){
            log.error("반경 탐색 에러", e);
            slackClient.sendMessage("반경 캡쳐 에러", e);
        } catch (LoadMapException e) {
            log.error("지도 로딩 에러", e);
            slackClient.sendMessage("지도 로딩 에러", e);
        } catch (Exception e){
            log.error("지도 가공 에러", e);
            slackClient.sendMessage("지도 가공 에러", e);
        }



        UserMapResponse response;
        ByteArrayResource byteArrayResource = null;
        try {
            byteArrayResource = driverService.capturePage();
        } catch (Exception e){
            log.error("지도 캡쳐 에러", e);
            slackClient.sendMessage("지도 캡쳐 에러", e);
        } finally {
            String uuid = UUID.randomUUID().toString();
            imageMap.put(uuid, byteArrayResource);
            response = UserMapResponse.builder()
                    .index(0)
                    .uuid(uuid)
                    .build();
        }

        return new CompletableFuture<Void>();
    }


    public Optional<ByteArrayResource> popImage(String uuid){
        Optional<ByteArrayResource> data = Optional.ofNullable(imageMap.get(uuid));
        imageMap.remove(uuid);

        return data;
    }
}