package com.joebrooks.mapshotimageapi.connection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConnectionInfoService {

    private final ConnectionInfoRepository connectionInfoRepository;

    public void add(ConnectionInfo info){
        connectionInfoRepository.save(info);
    }

    public void remove(ConnectionInfo info){
        connectionInfoRepository.delete(info);
    }

    public List<ConnectionInfo> getAll(){
        return (List<ConnectionInfo>) connectionInfoRepository.findAll();
    }

}
