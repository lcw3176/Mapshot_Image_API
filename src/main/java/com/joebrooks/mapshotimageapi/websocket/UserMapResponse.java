package com.joebrooks.mapshotimageapi.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMapResponse {

    @JsonProperty("index")
    private int index;

    @JsonProperty("x")
    private int x;

    @JsonProperty("y")
    private int y;

    @JsonProperty("done")
    private boolean done;

    @JsonProperty("uuid")
    private String uuid;

}