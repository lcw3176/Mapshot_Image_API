package com.joebrooks.mapshotimageapi.factory.driver.exception;

public class LoadPageException extends RuntimeException {

    public LoadPageException(Throwable e){
        super(e);
    }

    public LoadPageException(String s, Throwable e){
        super(s, e);
    }
}
