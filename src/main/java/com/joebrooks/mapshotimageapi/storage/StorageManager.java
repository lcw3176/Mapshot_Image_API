package com.joebrooks.mapshotimageapi.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
이미지를 임시로 보관하는 역할을 합니다.
uuid를 통해 이미지를 저장, 발급해줍니다.
*/
@Component
@RequiredArgsConstructor
@Slf4j
public class StorageManager {

    private static final Map<String, ByteArrayResource> imageMap = new HashMap<>();

    public void add(String uuid, ByteArrayResource imageResource){
        imageMap.put(uuid, imageResource);
    }

    public Optional<ByteArrayResource> popImage(String uuid){
        Optional<ByteArrayResource> data = Optional.ofNullable(imageMap.get(uuid));
        Optional.ofNullable(imageMap.get(uuid)).ifPresent(imageMap::remove);

        return data;
    }
}
