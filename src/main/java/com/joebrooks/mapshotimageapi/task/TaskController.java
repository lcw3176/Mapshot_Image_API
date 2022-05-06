package com.joebrooks.mapshotimageapi.task;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map/storage")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{uuid}")
    public ResponseEntity<ByteArrayResource> findCompletedImage(@PathVariable String uuid){
        ByteArrayResource arrayResource = taskService.popImage(uuid)
                .orElse(new ByteArrayResource(new byte[0]));

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(arrayResource.contentLength())
                .body(arrayResource);
    }
}