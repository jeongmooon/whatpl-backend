package com.whatpl.swagger.test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatpl.global.config.SecurityConfig;
import com.whatpl.global.jwt.JwtProperties;
import com.whatpl.global.jwt.JwtService;
import com.whatpl.global.security.domain.MemberPrincipal;
import com.whatpl.member.service.MemberLoginService;
import com.whatpl.swagger.test.data.ResponseData;
import com.whatpl.swagger.test.dto.MemberDTO;
import com.whatpl.swagger.test.service.SwaggerTestService;


@WebMvcTest
@AutoConfigureRestDocs //
@Import(SecurityConfig.class)
@ExtendWith(RestDocumentationExtension.class) //
public class SwaggerTest {
	
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SwaggerTestService swaggerTestService;
    
    @MockBean
    MemberLoginService memberLoginService;

    @MockBean
    JwtService jwtService;

    @MockBean
    JwtProperties jwtProperties;
    
    @Test
    @DisplayName("swagger 테스트")
	void getTest() throws Exception {
        // given: 토큰이 유효한 경우로 세팅
        String tokenType = "Bearer";
        String validToken = "validToken";
        var principal = new MemberPrincipal(1L, "test", "", Collections.emptySet(), null);
        var authenticationToken = new UsernamePasswordAuthenticationToken(principal, "", Collections.emptySet());
        when(jwtProperties.getTokenType())
                .thenReturn(tokenType);
        when(jwtService.resolveToken(any()))
                .thenReturn(authenticationToken);
    	
        // return dummy
        MemberDTO memReq = new MemberDTO("1","req@gmail.com","testReq");
		MemberDTO memRes = new MemberDTO("1","res@gmail.com","testRes");
		ResponseData<MemberDTO> res = new ResponseData<MemberDTO>("G000");
		res.setData(memRes);
		res.setAccessToken("AccessToken");
		
		when(swaggerTestService.getTest(any()))
			.thenReturn(res);
		
		mockMvc.perform(RestDocumentationRequestBuilders.post("/swagger/test")
					.contentType(MediaType.APPLICATION_JSON)
					.characterEncoding(StandardCharsets.UTF_8)
					.header(HttpHeaders.AUTHORIZATION, tokenType + validToken)
					.content(objectMapper.writeValueAsString(memReq)))
				.andDo(document(
					"/swagger/test",
					preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(headerWithName("Authorization")
                    			.description("AccessToken")
                    ),
                    requestFields(
                    		fieldWithPath("idx").description("아이디"),
                    		fieldWithPath("email").description("이메일"),
                    		fieldWithPath("password").description("비밀번호")
                    ),
                    responseFields(
                    		fieldWithPath("status").description("상태").type(JsonFieldType.STRING),
                    		fieldWithPath("message").description("메세지").type(JsonFieldType.STRING),
                    		fieldWithPath("accessToken").description("토큰").type(JsonFieldType.STRING),
                    		fieldWithPath("data.idx").description("아이디").type(JsonFieldType.STRING),
                    		fieldWithPath("data.email").description("이메일").type(JsonFieldType.STRING),
                    		fieldWithPath("data.password").description("비밀번호").type(JsonFieldType.STRING)
                    )
                    /*
                    ResourceDocumentation.resource(
                    		ResourceSnippetParameters.builder()
                    			.description("swagger 테스트")
                    			.requestFields(
                    					PayloadDocumentation.fieldWithPath("id").description("아이디")
                    			)
                    			.responseFields(
                    					new FieldDescriptors(
                    							PayloadDocumentation.fieldWithPath("data.email").description("이메일"),
                    							PayloadDocumentation.fieldWithPath("data.password").description("비밀번호")
                    					)
                    			)
                    			.requestSchema(Schema.schema("swagger Req"))
                    			.responseSchema(Schema.schema("swagger Res"))
                    			.build()
                    )*/
			))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
