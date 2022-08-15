package com.joebrooks.mapshotimageapi.processing;

import com.joebrooks.mapshotimageapi.global.IDataStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class ProcessingQueueTest {

    private IDataStore<Processing> processingIDataStore = new ProcessingQueue();

    @Test
    void 큐_성능_테스트(){
        long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

        for(int i = 0; i < 1000; i++){
            Processing processing = Processing.builder()
                    .mapWidth(1000)
                    .requestUri(null)
                    .session(null)
                    .build();


            processingIDataStore.add(processing);

        }


        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = afterTime - beforeTime; //두 시간에 차 계산
        System.out.println("기존 : "+secDiffTime);
    }


    @Test
    void 큐_성능_테스트2(){
        TestClass test = new TestClass();
        long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

        for(int i = 0; i < 1000; i++){
            Processing processing = Processing.builder()
                    .mapWidth(1000)
                    .requestUri(null)
                    .session(null)
                    .build();


            test.add(processing);

        }


        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = afterTime - beforeTime; //두 시간에 차 계산
        System.out.println("신규 : "+secDiffTime);
    }


    class TestClass implements IDataStore<Processing>{

        private final Queue<Processing> queue = new LinkedBlockingQueue<>();

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void add(Processing data) {
            queue.add(data);
        }

        @Override
        public Processing get(Object key) {
            return null;
        }
    }
}