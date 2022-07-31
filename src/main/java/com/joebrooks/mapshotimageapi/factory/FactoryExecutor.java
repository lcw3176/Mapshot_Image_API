package com.joebrooks.mapshotimageapi.factory;

import com.joebrooks.mapshotimageapi.factory.driver.DriverService;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.order.OrderService;
import com.joebrooks.mapshotimageapi.order.ResponseInfo;
import com.joebrooks.mapshotimageapi.storage.Storage;
import com.joebrooks.mapshotimageapi.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;


/*
알맞은 좌표의 위성 이미지를 요청한 후 이미지를 크롤링합니다.
한 번에 하나의 이미지 작업만 수행하며, 이미지를 분할해서 캡쳐한 후
해당 이미지가 전체 이미지 중 어느 위치에서 캡쳐된 이미지인지 나타내주는 x,y 좌표값과
uuid 값을 제작합니다.
*/

@Component
@RequiredArgsConstructor
@Slf4j
public class FactoryExecutor {

    private final StorageService storageService;
    private final OrderService orderService;
    private final DriverService driverService;
    private final SlackClient slackClient;
    private final FactoryService factoryService;

    @Value("${map.image.dividedWidth}")
    private int dividedWidth;


    @Scheduled(fixedDelay = 1000)
    public void execute() throws IOException {

        if(!factoryService.isEmpty()){
            FactoryTask task = factoryService.getTask();

            try{
                driverService.loadPage(task.getRequestUri());
                int width = task.getWidth();

                for(int y = 0; y < width; y+= dividedWidth){
                    for(int x = 0; x < width; x+= dividedWidth){

                        driverService.scrollPage(x, y);
                        byte[] imageByte = driverService.capturePage();

                        // 세션이 열려있다면 이미지 uuid 전송, 닫혔다면 작업 정지
                        if(task.getSession().isOpen()){
                            String uuid = UUID.randomUUID().toString();

                            storageService.add(Storage.builder()
                                    .uuid(uuid)
                                    .imageByte(imageByte)
                                    .build());

                            orderService.onComplete(
                                    ResponseInfo.builder()
                                            .index(0)
                                            .x(x)
                                            .y(y)
                                            .uuid(uuid)
                                            .session(task.getSession())
                                            .build()
                            );


                        } else {
                            storageService.clear();
                            return;
                        }
                    }
                }



            } catch (Exception e){

                storageService.clear();

                orderService.onComplete(
                        ResponseInfo.builder()
                                .index(0)
                                .error(true)
                                .session(task.getSession())
                                .build()
                );

                log.error(e.getMessage(), e);
                slackClient.sendMessage(e);

            } finally {
                // close 요청을 못보내고 연결이 끊길 경우를 대비해서
                // 세션은 항상 DB 목록에서 지운다
                orderService.onClose(task.getSession());
            }
        }
    }
}
