package com.joebrooks.mapshotimageapi.driver.exception;

public class ScreenshotException extends RuntimeException {

    public ScreenshotException(Throwable e) {
        super(e);
    }

    public ScreenshotException(String s, Throwable e){
        super(s, e);
    }
}
