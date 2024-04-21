package com.whatpl.product.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.request.RequestDocumentation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.whatpl.ApiDocTag;
import com.whatpl.BaseSecurityWebMvcTest;
import com.whatpl.global.security.model.WithMockWhatplMember;
import com.whatpl.product.domain.Product;
import com.whatpl.product.model.ProductCreateRequestFixture;
import com.whatpl.product.model.ProductFixture;
import com.whatpl.product.request.ProductCreateRequest;

@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public class ProductControllerTest extends BaseSecurityWebMvcTest {
	
	@Test
	@WithMockWhatplMember
	@DisplayName("프로덕트 등록")
	void create() throws Exception {
		ProductCreateRequest pcr = ProductCreateRequestFixture.create(true);
		String reqJson = objectMapper.writeValueAsString(pcr);
		MockMultipartFile request = new MockMultipartFile("request", "", MediaType.APPLICATION_JSON_VALUE, reqJson.getBytes(StandardCharsets.UTF_8));
		MockMultipartFile mockMultipartFile1 = createMockMultipartFile("portfolios", "cat.jpg", MediaType.IMAGE_JPEG_VALUE);
        MockMultipartFile mockMultipartFile2 = createMockMultipartFile("portfolios", "dummy.pdf", MediaType.IMAGE_PNG_VALUE);
		Long productId = 1L;
		when(productService.createProduct(any(ProductCreateRequest.class) ,anyList() ,anyLong()))
				.thenReturn(productId);
		
		mockMvc.perform(multipart("/product")
						.file(request)
						.file(mockMultipartFile1)
						.file(mockMultipartFile2)
						.header(HttpHeaders.AUTHORIZATION, "Bearer {AccessToken}")
						//.contentType(MediaType.APPLICATION_JSON_VALUE)
                        //.content(reqJson)
				)
				.andDo(print())
				.andDo(document("create-product", 
						resourceDetails().tag(ApiDocTag.PRODUCT.getTag())
						.summary("프로덕트 등록"),
						requestHeaders(
								headerWithName(HttpHeaders.AUTHORIZATION).description("AccessToken"),
								headerWithName(CONTENT_TYPE).description(MULTIPART_FORM_DATA_VALUE)
						),
						requestParts(
								partWithName("request").description("프로덕트 값"),
								partWithName("picutres").description("프로덕트 대표 사진")
						),
						requestPartFields("request",
								fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
								fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
								fieldWithPath("oneLine").type(JsonFieldType.STRING).description("한줄소개"),
								fieldWithPath("icon").type(JsonFieldType.STRING).description("아이콘"),
								fieldWithPath("emailForUt").type(JsonFieldType.STRING).description("연락이메일"),
								fieldWithPath("androidRink").type(JsonFieldType.STRING).description("안드로이드다운"),
								fieldWithPath("iosRink").type(JsonFieldType.STRING).description("IOS다운"),
								fieldWithPath("webRink").type(JsonFieldType.STRING).description("웹링크"),
								fieldWithPath("skills").type(JsonFieldType.ARRAY).description("기술스택")
						)
				));
	}
	
	@Test
	@WithMockWhatplMember
	@DisplayName("프로덕트 업데이트")
	void update() {
		Product product = ProductFixture.create(false);
	}
}
