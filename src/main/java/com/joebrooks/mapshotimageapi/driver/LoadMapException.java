package com.joebrooks.mapshotimageapi.driver;

public class LoadMapException extends RuntimeException {

    public LoadMapException(String s, Throwable e){
        super(s, e);
    }
}
