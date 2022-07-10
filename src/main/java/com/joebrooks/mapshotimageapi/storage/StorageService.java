package com.joebrooks.mapshotimageapi.storage;

import com.joebrooks.mapshotimageapi.global.memorydb.IMemoryDB;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

/*
이미지를 임시로 보관하는 역할을 합니다.
uuid를 통해 이미지를 저장, 발급해줍니다.
*/
@Service
@RequiredArgsConstructor
public class StorageService {

    private final IMemoryDB<StorageInfo> storageMemoryDB;

    public void addInfo(StorageInfo storageInfo){
        storageMemoryDB.add(storageInfo);
    }

    public void clearInfo(){
        storageMemoryDB.clear();
    }

    public ByteArrayResource getImage(String uuid){
        return storageMemoryDB.pop(uuid).getByteArrayResource();
    }
}
