package com.joebrooks.mapshotimageapi.deliver;

import com.joebrooks.mapshotimageapi.global.IData;
import com.joebrooks.mapshotimageapi.global.IDataReceiver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryReceiver implements IDataReceiver {

    private final DeliverService deliverService;

    @Override
    public boolean receive(IData data) {
        Delivery value = deliverService.setup((Delivery) data);

        return deliverService.sendInfo(value);
    }
}
