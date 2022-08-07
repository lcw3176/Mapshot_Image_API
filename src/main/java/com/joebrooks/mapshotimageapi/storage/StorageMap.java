package com.joebrooks.mapshotimageapi.storage;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StorageMap {

    private Map<String, Storage> map = new HashMap<>();


    public void save(Storage storage){
        map.put(storage.getUuid(), storage);
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
