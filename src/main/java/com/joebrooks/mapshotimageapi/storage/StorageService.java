package com.joebrooks.mapshotimageapi.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageMap storageMap;

    public void add(Storage storage){
        storageMap.save(storage);
    }

    public void clear(){
        storageMap.deleteAll();
    }

    public Storage pop(String uuid){
        Storage storage = storageMap.findByUuid(uuid);
        Storage copyValue = Storage.builder()
                .imageByte(storage.getImageByte())
                .build();
        storageMap.delete(uuid);

        return copyValue;
    }
}
