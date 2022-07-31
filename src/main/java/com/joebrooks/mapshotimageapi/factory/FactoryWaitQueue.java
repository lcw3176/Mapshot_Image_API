package com.joebrooks.mapshotimageapi.factory;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class FactoryWaitQueue {

    private final Queue<FactoryTask> factoryTaskQueue = new LinkedList<>();


    public synchronized boolean isEmpty() {
        return factoryTaskQueue.isEmpty();
    }


    public synchronized FactoryTask pop() {
        return factoryTaskQueue.poll();
    }


    public synchronized void add(FactoryTask value) {
        factoryTaskQueue.add(value);
    }
}
