package com.joebrooks.mapshotimageapi.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ByteArrayResource;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorageInfo {

    private String uuid;
    private ByteArrayResource byteArrayResource;
}
