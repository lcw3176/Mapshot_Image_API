package com.joebrooks.mapshotimageapi.processing;

import com.joebrooks.mapshotimageapi.global.IDataStore;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class ProcessingQueue implements IDataStore<Processing> {

    private final Queue<Processing> factoryTaskQueue = new LinkedList<>();


    @Override
    public synchronized boolean isEmpty() {
        return factoryTaskQueue.isEmpty();
    }


    @Override
    public synchronized void add(Processing value) {
        factoryTaskQueue.add(value);
    }

    @Override
    public synchronized Processing get(Object key) {
        return factoryTaskQueue.poll();
    }

}
