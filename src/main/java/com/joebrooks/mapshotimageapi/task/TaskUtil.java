package com.joebrooks.mapshotimageapi.task;

import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.experimental.UtilityClass;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@UtilityClass
public class TaskUtil {

    public UriComponents getUri(TaskRequest taskRequest){

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("kmapshot.com")
                .path("/map/gen/" + taskRequest.getCompanyType().toString())
                .queryParam("layerMode", taskRequest.isLayerMode())
                .queryParam("lat", taskRequest.getLat())
                .queryParam("lng", taskRequest.getLng())
                .queryParam("level", taskRequest.getLevel())
                .queryParam("type", taskRequest.getType())
                .build(true);
    }


    public int getWidth(TaskRequest taskRequest){

        return Arrays.stream(taskRequest.getCompanyType().getMapRadius())
                .filter(i -> i.getLevel() == taskRequest.getLevel())
                .findFirst()
                .orElseThrow(NoSuchElementException::new)
                .getWidth();
    }
}
