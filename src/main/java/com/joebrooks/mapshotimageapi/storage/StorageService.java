package com.joebrooks.mapshotimageapi.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;

    public void add(Storage storage){
        storageRepository.save(storage);
    }

    public void clear(){
        storageRepository.deleteAll();
    }

    public Storage pop(String uuid){
        Storage storage = storageRepository.findByUuid(uuid);
        Storage copyValue = Storage.builder()
                .imageByte(storage.getImageByte())
                .build();
        storageRepository.delete(storage);

        return copyValue;
    }
}
