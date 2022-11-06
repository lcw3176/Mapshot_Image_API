package com.joebrooks.mapshotimageapi.task;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.stereotype.Component;

@Component
public class TaskQueue {
    private final Queue<TaskRequest> taskRequestQueue = new ConcurrentLinkedQueue<>();


    public boolean isEmpty() {
        return taskRequestQueue.isEmpty();
    }


    public void add(TaskRequest value) {
        taskRequestQueue.offer(value);
    }

    public TaskRequest poll() {
        return taskRequestQueue.poll();
    }

    public int size(){
        return taskRequestQueue.size();
    }
}
