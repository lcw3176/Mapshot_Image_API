package com.joebrooks.mapshotimageapi.connection.response;

import com.joebrooks.mapshotimageapi.global.memorydb.IMemoryDB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketSessionService {


    private final IMemoryDB<WebSocketSession> sessionMemoryDB;

    public void addUser(WebSocketSession session){
        if(!sessionMemoryDB.contains(session)){
            sessionMemoryDB.add(session);
        }
    }

    public void removeUser(WebSocketSession session){
        sessionMemoryDB.remove(session);
    }

    public List<WebSocketSession> getAllUsers(){
        return sessionMemoryDB.getAll();
    }


}
