package com.joebrooks.mapshotimageapi.storage;

import com.joebrooks.mapshotimageapi.global.IDataStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final IDataStore<Storage> storageMap;

    public boolean isValidate(Storage storage){
      return !storage.isError();
    }

    public void add(Storage storage){
        storageMap.add(storage);
    }
}
