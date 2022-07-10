package com.joebrooks.mapshotimageapi.factory;

import com.joebrooks.mapshotimageapi.global.memorydb.IMemoryDB;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class FactoryMemoryDB implements IMemoryDB<FactoryTask> {

    private final Queue<FactoryTask> factoryTaskQueue = new LinkedList<>();


    @Override
    public synchronized boolean isEmpty() {
        return factoryTaskQueue.isEmpty();
    }


    @Override
    public synchronized FactoryTask pop() {
        return factoryTaskQueue.poll();
    }


    @Override
    public synchronized void add(FactoryTask value) {
        factoryTaskQueue.add(value);
    }


}
