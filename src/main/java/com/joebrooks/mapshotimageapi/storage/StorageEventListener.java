package com.joebrooks.mapshotimageapi.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageEventListener {

    private final StorageService storageService;

    @EventListener
    public void addImage(StorageInfo storageInfo){
        storageService.addInfo(storageInfo);
    }
}
