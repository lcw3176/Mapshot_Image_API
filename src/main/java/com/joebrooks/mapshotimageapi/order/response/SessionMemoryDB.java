package com.joebrooks.mapshotimageapi.order.response;

import com.joebrooks.mapshotimageapi.global.memorydb.IMemoryDB;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.LinkedList;
import java.util.List;

@Component
public class SessionMemoryDB implements IMemoryDB<WebSocketSession> {

    private final LinkedList<WebSocketSession> sessionList = new LinkedList<>();

    @Override
    public synchronized boolean contains(WebSocketSession session){
        return sessionList.contains(session);
    }

    @Override
    public synchronized void add(WebSocketSession session){
        sessionList.add(session);
    }

    @Override
    public synchronized void remove(WebSocketSession session){
        sessionList.remove(session);
    }

    @Override
    public synchronized List<WebSocketSession> getAll(){
        return sessionList;
    }


}
