package com.joebrooks.mapshotimageapi.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FactoryTaskListener {

    private final FactoryService factoryService;

    @EventListener
    public void addTask(FactoryTask factoryTask){
        factoryService.addTask(factoryTask);
    }
}
