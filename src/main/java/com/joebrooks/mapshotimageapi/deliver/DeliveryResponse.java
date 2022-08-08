package com.joebrooks.mapshotimageapi.deliver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryResponse {

    // 유저 대기 순번
    @JsonProperty("index")
    private int index;

    // 분할된 지도의 정보
    // ex) x=0, y=0
    // -> 이 지도 이미지는 분할된 전체 이미지중 첫 번째 위치의 지도
    @JsonProperty("x")
    private int x;

    @JsonProperty("y")
    private int y;

    // 서버에 보관된 이미지 정보
    // storage 컨트롤러에 요청해 해당되는 이미지를 발급받음
    @JsonProperty("uuid")
    private String uuid;

    // 에러 발생 플래그
    @JsonProperty("error")
    private boolean error;
}
