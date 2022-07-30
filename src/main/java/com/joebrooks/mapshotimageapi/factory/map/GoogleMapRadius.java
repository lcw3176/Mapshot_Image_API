package com.joebrooks.mapshotimageapi.factory.map;

public enum GoogleMapRadius implements IMapRadius {
    // 계산 편의상 width 크기 500px 크게 설정함
    ONE(1, 6000),
    TWO(2, 5000),
    FIVE(5, 6000),
    TEN(10, 6000);


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
