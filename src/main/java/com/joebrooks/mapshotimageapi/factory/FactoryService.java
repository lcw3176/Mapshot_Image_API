package com.joebrooks.mapshotimageapi.factory;


import com.joebrooks.mapshotimageapi.driver.DriverService;
import com.joebrooks.mapshotimageapi.global.model.UserMapResponse;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.global.util.UriGenerator;
import com.joebrooks.mapshotimageapi.global.util.WidthExtractor;
import com.joebrooks.mapshotimageapi.storage.StorageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

/*
알맞은 좌표의 위성 이미지를 요청한 후 이미지를 크롤링합니다.
한 번에 하나의 이미지 작업만 수행하며, 이미지를 분할해서 캡쳐한 후
해당 이미지가 전체 이미지 중 어느 위치에서 캡쳐된 이미지인지 나타내주는 x,y 좌표값과
uuid 값을 제작합니다.
*/

@Service
@RequiredArgsConstructor
@Slf4j
public class FactoryService {

    @Value("${map.image.dividedWidth}")
    private int dividedWidth;

    private final FactoryManager factoryManager;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final DriverService driverService;
    private final SlackClient slackClient;


    @PostConstruct
    private void init(){
        Thread thread = new Thread(() -> {

            while(true){
                while(!factoryManager.isEmpty()){
                    FactoryTask task = factoryManager.getTask();
                    boolean isRun = true;

                    try{
                        driverService.loadPage(UriGenerator.getUri(task.getUserMapRequest()));
                        int width = WidthExtractor.extract(task.getUserMapRequest());

                        for(int y = 0; y < width; y+= dividedWidth){
                            for(int x = 0; x < width; x+= dividedWidth){

                                driverService.scrollPage(x, y);
                                ByteArrayResource byteArrayResource = driverService.capturePage();

                                // 세션이 열려있다면 이미지 uuid 전송, 닫혔다면 작업 정지
                                if(task.getSession().isOpen()){
                                    String uuid = UUID.randomUUID().toString();

                                    applicationEventPublisher.publishEvent(StorageInfo.builder()
                                            .uuid(uuid)
                                            .byteArrayResource(byteArrayResource)
                                            .build());

                                    applicationEventPublisher.publishEvent(UserMapResponse.builder()
                                            .index(0)
                                            .x(x)
                                            .y(y)
                                            .uuid(uuid)
                                            .session(task.getSession())
                                            .build());

                                } else {
                                    isRun = false;
                                    break;
                                }
                            }

                            if(!isRun){
                                break;
                            }
                        }


                    } catch (Exception e){
                        applicationEventPublisher.publishEvent(UserMapResponse.builder()
                                .index(0)
                                .error(true)
                                .build());

                        log.error(e.getMessage(), e);
                        slackClient.sendMessage(e);
                    }
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        thread.start();
    }
}