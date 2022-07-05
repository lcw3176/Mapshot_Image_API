package com.joebrooks.mapshotimageapi.global.util;

import com.joebrooks.mapshotimageapi.global.model.UserMapRequest;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.NoSuchElementException;

@UtilityClass
public class WidthExtractor {

    public int extract(UserMapRequest request){
        return Arrays.stream(request.getCompanyType().getMapRadius())
                .filter(i -> i.getLevel() == request.getLevel())
                .findFirst()
                .orElseThrow(NoSuchElementException::new)
                .getWidth();
    }
}
