package com.joebrooks.mapshotimageapi.event;

import com.joebrooks.mapshotimageapi.factory.FactoryTask;
import com.joebrooks.mapshotimageapi.global.model.UserMapResponse;
import com.joebrooks.mapshotimageapi.storage.StorageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;


    public void toStorage(StorageInfo storageInfo){
        applicationEventPublisher.publishEvent(storageInfo);
    }

    public void toFactory(FactoryTask factoryTask){
        applicationEventPublisher.publishEvent(factoryTask);
    }

    public void toSession(UserMapResponse userMapResponse){
        applicationEventPublisher.publishEvent(userMapResponse);
    }
}
