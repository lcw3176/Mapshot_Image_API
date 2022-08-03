package com.joebrooks.mapshotimageapi.processing;


import com.joebrooks.mapshotimageapi.user.UserRequest;
import lombok.experimental.UtilityClass;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.NoSuchElementException;

@UtilityClass
public class ProcessingUtil {

    public UriComponents getUri(Processing processing){
        UserRequest order = processing.getRequest();

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("kmapshot.com")
                .path("/map/gen/" + order.getCompanyType().toString())
                .queryParam("layerMode", order.isLayerMode())
                .queryParam("lat", order.getLat())
                .queryParam("lng", order.getLng())
                .queryParam("level", order.getLevel())
                .queryParam("type", order.getType())
                .build(true);
    }


    public int getWidth(Processing processing){
        UserRequest request = processing.getRequest();

        return Arrays.stream(request.getCompanyType().getMapRadius())
                .filter(i -> i.getLevel() == request.getLevel())
                .findFirst()
                .orElseThrow(NoSuchElementException::new)
                .getWidth();
    }
}
