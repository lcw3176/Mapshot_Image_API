package com.joebrooks.mapshotimageapi.processing;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class ProcessingWaitQueue {

    private final Queue<Processing> factoryTaskQueue = new LinkedList<>();


    public synchronized boolean isEmpty() {
        return factoryTaskQueue.isEmpty();
    }


    public synchronized Processing pop() {
        return factoryTaskQueue.poll();
    }


    public synchronized void add(Processing value) {
        factoryTaskQueue.add(value);
    }
}
