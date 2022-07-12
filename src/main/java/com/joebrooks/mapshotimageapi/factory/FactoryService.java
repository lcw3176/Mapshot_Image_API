package com.joebrooks.mapshotimageapi.factory;


import com.joebrooks.mapshotimageapi.global.memorydb.IMemoryDB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FactoryService {


    private final IMemoryDB<FactoryTask> factoryMemoryDB;

    public void addTask(FactoryTask factoryTask){
        factoryMemoryDB.add(factoryTask);
    }

    public FactoryTask getTask(){
        return factoryMemoryDB.pop();
    }

    public boolean isEmpty(){
        return factoryMemoryDB.isEmpty();
    }
}