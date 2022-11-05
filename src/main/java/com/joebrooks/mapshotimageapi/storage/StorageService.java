package com.joebrooks.mapshotimageapi.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageMap storageMap;

    public boolean isValidate(Storage storage){
      return !storage.isError();
    }

    public void add(Storage storage){
        storageMap.put(storage);
    }

    public ByteArrayResource getImage(String uuid){
        ByteArrayResource image = new ByteArrayResource(storageMap.get(uuid).getImageByte());
        storageMap.remove(uuid);

        return image;
    }

    public List<Storage> getAll(){
        return storageMap.getAll();
    }

    public void remove(String uuid){
        storageMap.remove(uuid);
    }
}
