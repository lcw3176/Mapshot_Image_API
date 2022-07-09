package com.joebrooks.mapshotimageapi.global.model;


import com.joebrooks.mapshotimageapi.map.CompanyType;
import lombok.*;

@Getter
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

}
