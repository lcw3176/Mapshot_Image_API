package com.joebrooks.mapshotimageapi.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class StorageCleaner {

    private final StorageService storageService;

    @Scheduled(cron = "0 0 0 * * *")
    public void clean(){
        LocalDateTime nowTime = LocalDateTime.now();

        for(Storage i : storageService.getAll()){
            if(i.getCreatedAt().plusMinutes(30).isBefore(nowTime)){
                storageService.remove(i.getUuid());
            }
        }
    }

}
