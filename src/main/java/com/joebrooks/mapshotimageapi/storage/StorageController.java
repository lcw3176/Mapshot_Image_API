package com.joebrooks.mapshotimageapi.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map/storage")
public class StorageController {

    private final StorageManager storageManager;

    @GetMapping("/{uuid}")
    public ResponseEntity<ByteArrayResource> findCompletedImage(@PathVariable String uuid){
        ByteArrayResource arrayResource = storageManager.popImage(uuid)
                .orElse(new ByteArrayResource(new byte[0]));

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(arrayResource.contentLength())
                .body(arrayResource);
    }
}