package com.joebrooks.mapshotimageapi.factory.kakao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.factory.CompanyType;
import com.joebrooks.mapshotimageapi.global.model.UserMapRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class KakaoMapGeneratorControllerTest {


    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 카카오_지도_가져오기() throws Exception {
        UserMapRequest userMapRequest = UserMapRequest.builder()
                .lat(37.566968566622464)
                .lng(127.05034726055617)
                .companyType(CompanyType.kakao)
                .layerMode(false)
                .level(2)
                .type("basic")
                .build();


        MultiValueMap valueMap = new LinkedMultiValueMap<String, String>();
        Map<String, String> fieldMap = objectMapper.convertValue(userMapRequest, new TypeReference<Map<String, String>>() {});
        valueMap.setAll(fieldMap);

        mockMvc.perform(get("/map/gen/kakao")
                        .queryParams(valueMap))
                .andExpect(status().isOk())
                .andDo(document("get-kakao-map",
                        requestParameters(
                                parameterWithName("lat").description("위도"),
                                parameterWithName("lng").description("경도"),
                                parameterWithName("companyType").description("지도를 받아올 회사"),
                                parameterWithName("layerMode").description("도시 계획 레이어 적용 여부"),
                                parameterWithName("level").description("지도 확대 레벨"),
                                parameterWithName("type").description("지도 타입")
                        )
                    )
                );
    }

}