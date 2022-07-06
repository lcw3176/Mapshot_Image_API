package com.joebrooks.mapshotimageapi.factory;

import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class FactoryManager {

    private static final Queue<FactoryTask> userMapRequestsQueue = new ConcurrentLinkedQueue<>();

    public synchronized boolean isEmpty(){
        return userMapRequestsQueue.isEmpty();
    }

    public synchronized FactoryTask getTask(){
        return userMapRequestsQueue.poll();
    }

    public synchronized void addTask(FactoryTask factoryTask){
        userMapRequestsQueue.offer(factoryTask);
    }
}
