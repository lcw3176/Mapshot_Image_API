package com.joebrooks.mapshotimageapi.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageEventListener {

    private final StorageManager storageManager;

    @EventListener
    public void addImage(StorageInfo storageInfo){
        storageManager.add(storageInfo.getUuid(), storageInfo.getByteArrayResource());
    }
}
