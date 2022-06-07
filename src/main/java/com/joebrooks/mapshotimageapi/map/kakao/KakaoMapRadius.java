package com.joebrooks.mapshotimageapi.map.kakao;

import com.joebrooks.mapshotimageapi.map.IMapRadius;

public enum KakaoMapRadius implements IMapRadius {
    ONE(1, 5000),
    TWO(2, 4000),
    FIVE(5, 5000),
    TEN(10, 5000);

    private final int width;
    private final int level;

    private KakaoMapRadius(int level, int width){
        this.level = level;
        this.width = width;
    }


    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getWidth() {
        return this.width;
    }
}
