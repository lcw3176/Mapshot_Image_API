package com.joebrooks.mapshotimageapi.order;

import com.joebrooks.mapshotimageapi.global.IDataStore;
import com.joebrooks.mapshotimageapi.processing.Processing;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {


    @Test
    void 리스트_성능_테스트(){

        List<WebSocketSession> userSessionList = Collections.synchronizedList(new LinkedList<>());
        long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기


        for(int i = 0; i < 1000; i++){
            Thread t = new Thread(() -> {
                WebSocketSession session = new StandardWebSocketSession(null, null, null, null);


                userSessionList.add(session);
            });

            t.start();
        }


        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = afterTime - beforeTime; //두 시간에 차 계산
        System.out.println("기존 : "+secDiffTime);
    }


    @Test
    void 리스트_성능_테스트2(){

        TestClass test = new TestClass();
        long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기


        for(int i = 0; i < 1000; i++){
            Thread t = new Thread(() -> {
                WebSocketSession session = new StandardWebSocketSession(null, null, null, null);


                test.add(session);
            });

            t.start();
        }


        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = afterTime - beforeTime; //두 시간에 차 계산
        System.out.println("신규 : "+secDiffTime);
    }


    class TestClass {

        List<WebSocketSession> userSessionList = new LinkedList<>();

        public synchronized boolean isEmpty() {
            return false;
        }

        public synchronized void add(WebSocketSession data) {
            userSessionList.add(data);
        }

        public synchronized WebSocketSession get(Object key) {
            return null;
        }
    }
}