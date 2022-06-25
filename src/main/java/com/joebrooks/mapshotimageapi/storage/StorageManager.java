package com.joebrooks.mapshotimageapi.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
크롬 웹 드라이버의 동작을 관리합니다.
페이지를 로드, 스크롤, 캡쳐하는 기능을 포함하고 있습니다.
*/
@Component
@RequiredArgsConstructor
@Slf4j
public class StorageManager {

    private final static Map<String, ByteArrayResource> imageMap = new HashMap<>();

    public void add(String uuid, ByteArrayResource imageResource){
        imageMap.put(uuid, imageResource);
    }

    public Optional<ByteArrayResource> popImage(String uuid){
        Optional<ByteArrayResource> data = Optional.ofNullable(imageMap.get(uuid));
        Optional.ofNullable(imageMap.get(uuid)).ifPresent(imageMap::remove);

        return data;
    }
}
