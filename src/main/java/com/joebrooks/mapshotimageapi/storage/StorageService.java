package com.joebrooks.mapshotimageapi.storage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

/*
이미지를 임시로 보관하는 역할을 합니다.
uuid를 통해 이미지를 저장, 발급해줍니다.
*/
@Service
public class StorageService {

    private final StorageMemoryDB storageMemoryDB = StorageMemoryDB.getInstance();

    public void addInfo(StorageInfo storageInfo){
        storageMemoryDB.add(storageInfo.getUuid(), storageInfo.getByteArrayResource());
    }

    public void clearInfo(){
        storageMemoryDB.clear();
    }

    public ByteArrayResource getImage(String uuid){
        return storageMemoryDB.pop(uuid)
                .orElse(new ByteArrayResource(new byte[0]));
    }
}
