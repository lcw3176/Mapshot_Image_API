package com.joebrooks.mapshotimageapi.order;


import com.joebrooks.mapshotimageapi.factory.map.CompanyType;
import lombok.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMapRequest {

    // 도시 계획 레이어 적용 여부
    private boolean layerMode;

    // 요청한 지도 이미지의 중심 위도
    private double lat;

    // 요청한 지도 이미지의 중심 경도
    private double lng;

    // 지도 반경 값
    // ex) 1 -> 반경 1km의 지도
    private int level;

    // 지도 생성 타입
    // ex) satellite_base -> 위성 사진 기반의 지도
    private String type;

    // 지도 이미지를 가져올 회사 타입
    // ex) kakao -> 카카오 지도를 이용해서 이미지 생성
    private CompanyType companyType;


    public UriComponents getUri(){

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("kmapshot.com")
                .path("/map/gen/" + this.getCompanyType().toString())
                .queryParam("layerMode", this.isLayerMode())
                .queryParam("lat", this.getLat())
                .queryParam("lng", this.getLng())
                .queryParam("level", this.getLevel())
                .queryParam("type", this.getType())
                .build(true);
    }


    public int getWidth(){
        return Arrays.stream(this.getCompanyType().getMapRadius())
                .filter(i -> i.getLevel() == this.getLevel())
                .findFirst()
                .orElseThrow(NoSuchElementException::new)
                .getWidth();
    }

}
