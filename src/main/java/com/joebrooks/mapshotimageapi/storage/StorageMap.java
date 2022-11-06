package com.joebrooks.mapshotimageapi.storage;


import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StorageMap {

    private final Map<String, Storage> map = new HashMap<>();


    public boolean isEmpty() {
        return map.isEmpty();
    }

    public void put(Storage data) {
        map.put(data.getUuid(), data);
    }


    public Storage get(String  uuid) {
        return map.get(uuid);
    }

    public void remove(String  key) {
        map.remove(key);
    }

    public List<Storage> getAll(){
        List<Storage> temp = new LinkedList<>();

        for(String i : map.keySet()){
            temp.add(map.get(i));
        }

        return temp;
    }
}
