package com.joebrooks.mapshotimageapi.processing.driver.exception;

public class ScrollException extends RuntimeException {

    public ScrollException(Throwable e){
        super(e);
    }

    public ScrollException(String s, Throwable e){
        super(s, e);
    }
}
