package com.joebrooks.mapshotimageapi.order.request.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.processing.map.CompanyType;
import com.joebrooks.mapshotimageapi.order.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderSocketHandlerTest {

    @Test
    void 매핑_테스트() throws JsonProcessingException {
        Order order = Order.builder()
                .layerMode(false)
                .lat(0)
                .lng(0)
                .level(1)
                .type("basic")
                .companyType(CompanyType.google)
                .build();

        String str = "{\"layerMode\":\"false\",\"lat\":\"0\",\"lng\":\"0\",\"level\":\"1\",\"type\":\"basic\",\"companyType\":\"google\"}";

        ObjectMapper mapper = new ObjectMapper();

        Order result = mapper.readValue(str, Order.class);

        assertEquals(result.isLayerMode(), order.isLayerMode());
        assertEquals(result.getCompanyType(), order.getCompanyType());
        assertEquals(result.getLevel(), order.getLevel());
        assertEquals(result.getLat(), order.getLat());
        assertEquals(result.getLng(), order.getLng());
        assertEquals(result.getType(), order.getType());
    }
}