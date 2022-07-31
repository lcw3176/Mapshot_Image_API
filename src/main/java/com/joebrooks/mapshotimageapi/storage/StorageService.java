package com.joebrooks.mapshotimageapi.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
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

    public ByteArrayResource getImage(String uuid){
        Storage storage = storageRepository.findByUuid(uuid);
        ByteArrayResource byteArrayResource = new ByteArrayResource(storage.getImageByte());
        storageRepository.delete(storage);

        return byteArrayResource;
    }
}
