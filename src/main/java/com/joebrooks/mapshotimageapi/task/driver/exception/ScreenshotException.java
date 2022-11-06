package com.joebrooks.mapshotimageapi.task.driver.exception;

public class ScreenshotException extends RuntimeException {

    public ScreenshotException(Throwable e) {
        super(e);
    }

    public ScreenshotException(String s, Throwable e){
        super(s, e);
    }
}
