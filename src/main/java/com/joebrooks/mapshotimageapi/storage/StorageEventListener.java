package com.joebrooks.mapshotimageapi.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageEventListener {

    private final StorageService storageService;

    @EventListener
    public void addImage(StorageInfo storageInfo){

        switch (storageInfo.getCommand()){
            case PUT:
                storageService.addInfo(storageInfo);
                break;

            case CLEAR:
                storageService.clearInfo();
                break;

            default:
                break;

        }

    }
}
