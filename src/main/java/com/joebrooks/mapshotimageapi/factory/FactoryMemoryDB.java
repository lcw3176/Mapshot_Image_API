package com.joebrooks.mapshotimageapi.factory;

import java.util.LinkedList;
import java.util.Queue;

public class FactoryMemoryDB {

    private static final Queue<FactoryTask> userMapRequestsQueue = new LinkedList<>();
    private static final FactoryMemoryDB instance = new FactoryMemoryDB();

    private FactoryMemoryDB(){

    }

    public static FactoryMemoryDB getInstance(){
        return instance;
    }

    public synchronized boolean isEmpty(){
        return userMapRequestsQueue.isEmpty();
    }

    public synchronized FactoryTask poll(){
        return userMapRequestsQueue.poll();
    }

    public synchronized void offer(FactoryTask factoryTask){
        userMapRequestsQueue.offer(factoryTask);
    }

}
