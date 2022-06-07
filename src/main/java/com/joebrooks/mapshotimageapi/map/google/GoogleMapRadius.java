package com.joebrooks.mapshotimageapi.map.google;

import com.joebrooks.mapshotimageapi.map.IMapRadius;

public enum GoogleMapRadius implements IMapRadius {
    ONE(1, 5500),
    TWO(2, 4500),
    FIVE(5, 5500),
    TEN(10, 5500);


    private final int width;
    private final int level;

    private GoogleMapRadius(int level, int width){
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
