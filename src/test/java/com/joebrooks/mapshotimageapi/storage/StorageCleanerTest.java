package com.joebrooks.mapshotimageapi.storage;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StorageCleanerTest {

    private final StorageService storageService = new StorageService(new StorageMap());

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