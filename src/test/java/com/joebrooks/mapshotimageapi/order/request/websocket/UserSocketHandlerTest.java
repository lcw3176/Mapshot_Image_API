package com.joebrooks.mapshotimageapi.order.request.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.map.CompanyType;
import com.joebrooks.mapshotimageapi.order.request.UserMapRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSocketHandlerTest {

    @Test
    void 매핑_테스트() throws JsonProcessingException {
        UserMapRequest userMapRequest = UserMapRequest.builder()
                .layerMode(false)
                .lat(0)
                .lng(0)
                .level(1)
                .type("basic")
                .companyType(CompanyType.google)
                .build();

        String str = "{\"layerMode\":\"false\",\"lat\":\"0\",\"lng\":\"0\",\"level\":\"1\",\"type\":\"basic\",\"companyType\":\"google\"}";

        ObjectMapper mapper = new ObjectMapper();

        UserMapRequest result = mapper.readValue(str, UserMapRequest.class);

        assertEquals(result.isLayerMode(), userMapRequest.isLayerMode());
        assertEquals(result.getCompanyType(), userMapRequest.getCompanyType());
        assertEquals(result.getLevel(), userMapRequest.getLevel());
        assertEquals(result.getLat(), userMapRequest.getLat());
        assertEquals(result.getLng(), userMapRequest.getLng());
        assertEquals(result.getType(), userMapRequest.getType());
    }
}