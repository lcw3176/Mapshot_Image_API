package com.joebrooks.mapshotimageapi.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FactoryTaskListener {

    private final FactoryManager factoryManager;

    @EventListener
    public void addTask(FactoryTask factoryTask){
        factoryManager.addTask(factoryTask);
    }
}
