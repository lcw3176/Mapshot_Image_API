package com.joebrooks.mapshotimageapi.processing;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessingService {


    private final ProcessingWaitQueue processingWaitQueue;

    public void addTask(Processing processing){
        processingWaitQueue.add(processing);
    }

    public Processing getTask(){
        return processingWaitQueue.pop();
    }

    public boolean isEmpty(){
        return processingWaitQueue.isEmpty();
    }
}