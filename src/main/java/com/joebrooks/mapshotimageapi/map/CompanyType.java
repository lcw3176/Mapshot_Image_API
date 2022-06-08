package com.joebrooks.mapshotimageapi.map;

import com.joebrooks.mapshotimageapi.map.google.GoogleMapRadius;
import com.joebrooks.mapshotimageapi.map.kakao.KakaoMapRadius;
import lombok.Getter;

@Getter
public enum CompanyType {
    kakao(new IMapRadius[]{
            KakaoMapRadius.ONE,
            KakaoMapRadius.TWO,
            KakaoMapRadius.FIVE,
            KakaoMapRadius.TEN }),

    google(new IMapRadius[]{
            GoogleMapRadius.ONE,
            GoogleMapRadius.TWO,
            GoogleMapRadius.FIVE,
            GoogleMapRadius.TEN });

    private final IMapRadius[] mapRadius;

    private CompanyType(IMapRadius[] mapRadius){
        this.mapRadius = mapRadius;
    }
}