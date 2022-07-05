package com.joebrooks.mapshotimageapi.global.model;


import com.joebrooks.mapshotimageapi.factory.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
