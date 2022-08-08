package com.joebrooks.mapshotimageapi.order;

import lombok.experimental.UtilityClass;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.NoSuchElementException;

@UtilityClass
public class OrderUtil {

    public UriComponents getUri(Order order){

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


    public int getWidth(Order order){

        return Arrays.stream(order.getCompanyType().getMapRadius())
                .filter(i -> i.getLevel() == order.getLevel())
                .findFirst()
                .orElseThrow(NoSuchElementException::new)
                .getWidth();
    }
}
