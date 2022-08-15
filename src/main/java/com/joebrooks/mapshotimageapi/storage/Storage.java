package com.joebrooks.mapshotimageapi.storage;

import com.joebrooks.mapshotimageapi.global.IData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Storage implements IData {

    private String uuid;
    private byte[] imageByte;
    private boolean error;
    private LocalDateTime createdAt;
}

