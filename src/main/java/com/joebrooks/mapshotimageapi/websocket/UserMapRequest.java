package com.joebrooks.mapshotimageapi.websocket;


import com.joebrooks.mapshotimageapi.map.CompanyType;
import lombok.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMapRequest {

    private boolean layerMode;
    private double lat;
    private double lng;
    private int level;
    private String type;
    private CompanyType companyType;


    public UriComponents getUri(){

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("kmapshot.com")
                .path("/map/gen/" + this.getCompanyType().toString())
                .queryParam("layerMode", this.isLayerMode())
                .queryParam("lat", this.getLat())
                .queryParam("lng", this.getLng())
                .queryParam("level", this.getLevel())
                .queryParam("type", this.getType())
                .build(true);
    }


    public int getWidth(){
        return Arrays.stream(this.getCompanyType().getMapRadius())
                .filter(i -> i.getLevel() == this.getLevel())
                .findFirst()
                .orElseThrow(NoSuchElementException::new)
                .getWidth();
    }

}
