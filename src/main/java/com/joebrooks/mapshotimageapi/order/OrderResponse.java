package com.joebrooks.mapshotimageapi.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    // 유저 대기 순번
    @JsonProperty("index")
    private int index;


    // 에러 발생 플래그    //todo 자바스크립트쪽 코드좀 바꿔야 할듯
    @JsonProperty("error")
    private boolean error;
}
