package com.joebrooks.mapshotimageapi.storage;

import com.joebrooks.mapshotimageapi.global.memorydb.IMemoryDB;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StorageMemoryDB implements IMemoryDB<StorageInfo> {

    private final Map<String, ByteArrayResource> imageMap = new ConcurrentHashMap<>();

    @Override
    public void add(StorageInfo value) {
        imageMap.put(value.getUuid(), value.getByteArrayResource());
    }

    @Override
    public StorageInfo pop(Object key) {

        StorageInfo storageInfo = StorageInfo.builder()
                .byteArrayResource(imageMap.get(key.toString()))
                .build();

        imageMap.remove(key.toString());

        return storageInfo;
    }

    @Override
    public void clear(){
        imageMap.clear();
    }

}
