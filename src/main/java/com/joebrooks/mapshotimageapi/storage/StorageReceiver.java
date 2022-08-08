package com.joebrooks.mapshotimageapi.storage;

import com.joebrooks.mapshotimageapi.global.IData;
import com.joebrooks.mapshotimageapi.global.IDataReceiver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageReceiver implements IDataReceiver {


    private final StorageService storageService;

    @Override
    public boolean receive(IData data) {
        Storage value = (Storage) data;

        if(storageService.isValidate(value)){
            storageService.add(value);
        }

        return true;
    }
}
