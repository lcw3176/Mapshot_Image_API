package com.joebrooks.mapshotimageapi.processing;

import com.joebrooks.mapshotimageapi.deliver.Delivery;
import com.joebrooks.mapshotimageapi.global.IDataReceiver;
import com.joebrooks.mapshotimageapi.global.IDataStore;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.processing.driver.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;


/*
알맞은 좌표의 위성 이미지를 요청한 후 이미지를 크롤링합니다.
한 번에 하나의 이미지 작업만 수행하며, 이미지를 분할해서 캡쳐한 후
해당 이미지가 전체 이미지 중 어느 위치에서 캡쳐된 이미지인지 나타내주는 x,y 좌표값과
uuid 값을 제작합니다.
*/

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessWorker {

    private final IDataReceiver deliveryReceiver;

    private final DriverService driverService;
    private final SlackClient slackClient;
    private final IDataStore<Processing> processingQueue;

    @Value("${map.image.dividedWidth}")
    private int dividedWidth;


    @Scheduled(fixedDelay = 1000)
    public void execute() throws IOException {

        if(!processingQueue.isEmpty()){
            Processing task = processingQueue.get(null);

            try{

                driverService.loadPage(task.getRequestUri());
                int width = task.getMapWidth();

                for(int y = 0; y < width; y+= dividedWidth){
                    for(int x = 0; x < width; x+= dividedWidth){

                        driverService.scrollPage(x, y);
                        byte[] imageByte = driverService.capturePage();

                        Delivery delivery = Delivery.builder()
                                        .index(0)
                                        .x(x)
                                        .y(y)
                                        .session(task.getSession())
                                        .imageByte(imageByte)
                                        .build();

                        if(!deliveryReceiver.receive(delivery)){
                            return;
                        }

                    }
                }


            } catch (Exception e){

                Delivery delivery = Delivery.builder()
                        .index(0)
                        .session(task.getSession())
                        .error(true)
                        .build();

                deliveryReceiver.receive(delivery);

                log.error(e.getMessage(), e);
                slackClient.sendMessage(e);

            } finally {
                task.getSession().close();
            }
        }


    }

}
