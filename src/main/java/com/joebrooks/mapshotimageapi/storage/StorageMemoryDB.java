package com.joebrooks.mapshotimageapi.storage;

import org.springframework.core.io.ByteArrayResource;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class StorageMemoryDB {

    private static final Map<String, ByteArrayResource> imageMap = new ConcurrentHashMap<>();
    private static final StorageMemoryDB instance = new StorageMemoryDB();

    private StorageMemoryDB(){

    }

    public static StorageMemoryDB getInstance(){
        return instance;
    }

    public void add(String uuid, ByteArrayResource imageResource){
        imageMap.put(uuid, imageResource);
    }

    public void clear(){
        imageMap.clear();
    }

    public Optional<ByteArrayResource> pop(String uuid){
        Optional<ByteArrayResource> data = Optional.ofNullable(imageMap.get(uuid));
        Optional.ofNullable(imageMap.get(uuid)).ifPresent(imageMap::remove);

        return data;
    }
}
