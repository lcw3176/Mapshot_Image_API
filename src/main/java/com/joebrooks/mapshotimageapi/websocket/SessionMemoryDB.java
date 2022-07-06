package com.joebrooks.mapshotimageapi.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.LinkedList;
import java.util.List;

public class SessionMemoryDB {

    private static final LinkedList<WebSocketSession> sessionList = new LinkedList<>();
    private static final SessionMemoryDB instance = new SessionMemoryDB();

    private SessionMemoryDB(){

    }

    public static SessionMemoryDB getInstance(){
        return instance;
    }

    public synchronized boolean contains(WebSocketSession session){
        return sessionList.contains(session);
    }


    public synchronized void add(WebSocketSession session){
        sessionList.add(session);
    }

    public synchronized void remove(WebSocketSession session){
        sessionList.remove(session);
    }

    public synchronized List<WebSocketSession> getSessions(){
        return sessionList;
    }


}
