package com.joebrooks.mapshotimageapi.global.memorydb;

import java.util.Collections;
import java.util.List;

public interface IMemoryDB<T>{

    default boolean isEmpty(){
        return false;
    }

    default boolean contains(T value){
        return false;
    }

    default T pop(){
        return null;
    }

    default T pop(Object key){
        return null;
    }


    default List<T> getAll(){
        return Collections.emptyList();
    }

    void add(T value);

    default void remove(T value){ }

    default void clear(){ }

}
