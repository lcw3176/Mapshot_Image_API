package com.joebrooks.mapshotimageapi.global.util;

import com.joebrooks.mapshotimageapi.factory.UserMapRequest;
import lombok.experimental.UtilityClass;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@UtilityClass
public class UriGenerator {

    public UriComponents getUri(UserMapRequest userMapRequest){

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("kmapshot.com")
                .path("/map/gen/" + userMapRequest.getCompanyType().toString())
                .queryParam("layerMode", userMapRequest.isLayerMode())
                .queryParam("lat", userMapRequest.getLat())
                .queryParam("lng", userMapRequest.getLng())
                .queryParam("level", userMapRequest.getLevel())
                .queryParam("type", userMapRequest.getType())
                .build(true);
    }
}
