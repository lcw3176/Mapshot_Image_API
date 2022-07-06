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

    private final StorageService storageService;

    @GetMapping("/{uuid}")
    public ResponseEntity<ByteArrayResource> findCompletedImage(@PathVariable String uuid){
        ByteArrayResource imageArrayResource = storageService.getImage(uuid);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(imageArrayResource.contentLength())
                .body(imageArrayResource);
    }
}