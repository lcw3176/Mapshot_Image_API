package com.joebrooks.mapshotimageapi.global.util;

import com.joebrooks.mapshotimageapi.map.IMapRadius;
import com.joebrooks.mapshotimageapi.map.UserMapRequest;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

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
