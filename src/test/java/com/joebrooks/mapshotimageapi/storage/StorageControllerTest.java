package com.joebrooks.mapshotimageapi.storage;

import com.joebrooks.mapshotimageapi.factory.FactoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class StorageControllerTest {


    @Autowired
    private MockMvc mockMvc;
//
//    @SpyBean
//    private StorageManager storageManager;
//
//    @Test
//    void 이미지_반환() throws Exception {
//        Mockito.when(storageManager.popImage(Mockito.any()))
//                .thenReturn(java.util.Optional.of(new ByteArrayResource(new byte[100])));
//
//
//        mockMvc.perform(get("/map/storage/{uuid}", UUID.randomUUID())
//                ).andExpect(status().isOk())
//                 .andDo(
//                         document("get-done-image",
//                                 pathParameters(
//                                         parameterWithName("uuid").description("이미지 UUID")
//                                 )
//                         )
//                 );
//    }
}