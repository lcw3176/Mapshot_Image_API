package com.joebrooks.mapshotimageapi.map;

import lombok.Getter;

@Getter
public enum CompanyType {
    KAKAO(new IMapRadius[]{
            KakaoMapRadius.ONE,
            KakaoMapRadius.TWO,
            KakaoMapRadius.FIVE,
            KakaoMapRadius.TEN }),

    GOOGLE(new IMapRadius[]{
            GoogleMapRadius.ONE,
            GoogleMapRadius.TWO,
            GoogleMapRadius.FIVE,
            GoogleMapRadius.TEN });

    private final IMapRadius[] mapRadius;

    private CompanyType(IMapRadius[] mapRadius){
        this.mapRadius = mapRadius;
    }
}