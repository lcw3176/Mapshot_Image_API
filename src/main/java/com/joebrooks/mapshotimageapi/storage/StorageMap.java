package com.joebrooks.mapshotimageapi.storage;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class StorageMap {

    private Map<String, Storage> map = new HashMap<>();


    public String save(Storage storage){
        String uuid = UUID.randomUUID().toString();

        map.put(uuid, storage);

        return uuid;
    }

    public void deleteAll(){
        map.clear();
    }

    public Storage findByUuid(String uuid){
        return map.get(uuid);
    }

    public void delete(String uuid){
        map.remove(uuid);
    }

}
