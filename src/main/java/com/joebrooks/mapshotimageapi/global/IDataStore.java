package com.joebrooks.mapshotimageapi.global;

import java.util.Collections;
import java.util.List;

public interface IDataStore<T extends IData> {

    boolean isEmpty();
    void add(T data);
    T get(Object key);

    default List<T> getAll(){
        return Collections.emptyList();
    }


    default void remove(Object key){

    }
}
