package com.joebrooks.mapshotimageapi.factory;

import com.joebrooks.mapshotimageapi.global.model.UserMapRequest;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FactoryMemoryDBTest {

    private final FactoryMemoryDB factoryMemoryDB = new FactoryMemoryDB();
    private final Queue<FactoryTask> queue = new LinkedList<>();

    @Test
    void 동시성_테스트(){
        int testSize = 100;

        for(int i = 0; i < testSize; i++){
            queue.add(FactoryTask.builder()
                    .userMapRequest(UserMapRequest.builder()
                            .level(i)
                            .build())
                    .build());
        }

        for(int i = 0; i < testSize; i++){

            Thread thread = new Thread(() -> {
                factoryMemoryDB.add(queue.poll());
            });

            thread.start();
        }


        while(true){

            if(queue.size() <= 0){
                for(int i = 0; i < testSize; i++){
                    assertEquals(i, factoryMemoryDB.pop().getUserMapRequest().getLevel());
                }

                assertTrue(factoryMemoryDB.isEmpty());
                break;
            }
        }


    }
}