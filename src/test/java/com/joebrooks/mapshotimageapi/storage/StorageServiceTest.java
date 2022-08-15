package com.joebrooks.mapshotimageapi.storage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StorageServiceTest {

    private final StorageService storageService = new StorageService(new StorageMap());



    @Test
    void 이미지_꺼낸후_삭제_잘되는지(){
        String uuid = UUID.randomUUID().toString();
        storageService.add(Storage.builder()
                .createdAt(LocalDateTime.now())
                .error(false)
                .imageByte(new byte[20])
                .uuid(uuid)
                .build());

        storageService.getImage(uuid);
        assertEquals(0, storageService.getAll().size());
    }

}