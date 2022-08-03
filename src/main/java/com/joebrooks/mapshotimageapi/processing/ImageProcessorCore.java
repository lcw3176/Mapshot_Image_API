package com.joebrooks.mapshotimageapi.processing;

import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.processing.driver.DriverService;
import com.joebrooks.mapshotimageapi.storage.Storage;
import com.joebrooks.mapshotimageapi.storage.StorageService;
import com.joebrooks.mapshotimageapi.user.UserResponse;
import com.joebrooks.mapshotimageapi.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
public class ImageProcessorCore {

    private final UserService userService;
    private final StorageService storageService;

    private final DriverService driverService;
    private final SlackClient slackClient;
    private final ProcessingService processingService;

    @Value("${map.image.dividedWidth}")
    private int dividedWidth;


    @Scheduled(fixedDelay = 1000)
    public void execute() {

        if(!processingService.isEmpty()){
            Processing task = processingService.getTask();

            try{

                driverService.loadPage(ProcessingUtil.getUri(task));
                int width = ProcessingUtil.getWidth(task);

                for(int y = 0; y < width; y+= dividedWidth){
                    for(int x = 0; x < width; x+= dividedWidth){

                        driverService.scrollPage(x, y);
                        byte[] imageByte = driverService.capturePage();

                        String uuid = UUID.randomUUID().toString();

                        UserResponse response = UserResponse.builder()
                                        .index(0)
                                        .x(x)
                                        .y(y)
                                        .uuid(uuid)
                                        .build();

                        storageService.add(Storage.builder()
                                .uuid(uuid)
                                .imageByte(imageByte)
                                .build());

                        if(!userService.sendInfo(response, task.getSession())){
                            storageService.clear();

                            return;
                        }


                    }
                }


            } catch (Exception e){
                storageService.clear();

                UserResponse response = UserResponse.builder()
                        .index(0)
                        .error(true)
                        .build();

                userService.sendInfo(response, task.getSession());

                log.error(e.getMessage(), e);
                slackClient.sendMessage(e);

            } finally {
                userService.onClose(task.getSession());
            }
        }
    }

}
