package com.joebrooks.mapshotimageapi.task;

import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.storage.Storage;
import com.joebrooks.mapshotimageapi.storage.StorageService;
import com.joebrooks.mapshotimageapi.task.driver.DriverService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskWorker {


    private final DriverService driverService;
    private final SlackClient slackClient;
    private final TaskService taskService;
    private final StorageService storageService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${map.image.dividedWidth}")
    private int dividedWidth;


    @Scheduled(fixedDelay = 1000)
    public void execute() {

        if(!taskService.isTaskEmpty()){
            TaskRequest task = taskService.getUserRequestTask();

            try{
                driverService.loadPage(TaskUtil.getUri(task));
                int width = TaskUtil.getWidth(task);

                for(int y = 0; y < width; y+= dividedWidth){
                    for(int x = 0; x < width; x+= dividedWidth){

                        driverService.scrollPage(x, y);
                        byte[] imageByte = driverService.capturePage();
                        String uuid = UUID.randomUUID().toString();

                        storageService.add(Storage.builder()
                                        .createdAt(LocalDateTime.now())
                                        .uuid(uuid)
                                        .imageByte(imageByte)
                                        .build());

                        eventPublisher.publishEvent(TaskResponse.builder()
                                .sessionId(task.getSessionId())
                                .uuid(uuid)
                                .error(false)
                                .x(x)
                                .y(y)
                                .build());

                    }
                }

            } catch (Exception e){
                eventPublisher.publishEvent(TaskResponse.builder()
                        .sessionId(task.getSessionId())
                        .error(true)
                        .build());

                log.error(e.getMessage(), e);
                slackClient.sendMessage(e);

            }
        }


    }
}
