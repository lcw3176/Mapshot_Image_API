package com.joebrooks.mapshotimageapi.processing;

import com.joebrooks.mapshotimageapi.global.IData;
import com.joebrooks.mapshotimageapi.global.IDataReceiver;
import com.joebrooks.mapshotimageapi.global.IDataStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessingReceiver implements IDataReceiver {

    private final IDataStore<Processing> processingQueue;

    @Override
    public boolean receive(IData data) {
        processingQueue.add((Processing) data);

        return true;
    }
}
