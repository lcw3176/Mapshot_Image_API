package com.joebrooks.mapshotimageapi.factory;

import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class FactoryManager {

    private volatile Queue<FactoryTask> userMapRequestsQueue = new ConcurrentLinkedQueue<>();

    public boolean isEmpty(){
        return this.userMapRequestsQueue.isEmpty();
    }

    public FactoryTask getTask(){
        return this.userMapRequestsQueue.poll();
    }

    public void addTask(FactoryTask factoryTask){
        this.userMapRequestsQueue.offer(factoryTask);
    }
}
