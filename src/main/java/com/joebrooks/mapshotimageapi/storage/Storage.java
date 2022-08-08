package com.joebrooks.mapshotimageapi.storage;

import com.joebrooks.mapshotimageapi.global.IData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Storage implements IData {

    private String uuid;
    private byte[] imageByte;
    private boolean error;

    // todo
    // 생성 시각 추가하고
    // 스케줄러로 잉여 파일들 정리할 예정
}

