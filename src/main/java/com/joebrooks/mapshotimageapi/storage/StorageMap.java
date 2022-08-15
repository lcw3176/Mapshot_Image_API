package com.joebrooks.mapshotimageapi.storage;

import com.joebrooks.mapshotimageapi.global.IDataStore;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StorageMap implements IDataStore<Storage> {

    private final Map<String, Storage> map = new HashMap<>();


    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void add(Storage data) {
        map.put(data.getUuid(), data);
    }

    @Override
    public Storage get(Object uuid) {
        return map.get(uuid);
    }

    @Override
    public void remove(Object key) {
        map.remove(key);
    }

    @Override
    public List<Storage> getAll(){
        List<Storage> temp = new LinkedList<>();

        for(String i : map.keySet()){
            temp.add(map.get(i));
        }

        return temp;
    }
}
