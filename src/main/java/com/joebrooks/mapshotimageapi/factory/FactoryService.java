package com.joebrooks.mapshotimageapi.factory;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FactoryService {


    private final FactoryWaitQueue factoryWaitQueue;

    public void addTask(FactoryTask factoryTask){
        factoryWaitQueue.add(factoryTask);
    }

    public FactoryTask getTask(){
        return factoryWaitQueue.pop();
    }

    public boolean isEmpty(){
        return factoryWaitQueue.isEmpty();
    }
}