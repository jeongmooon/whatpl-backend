package com.whatpl.jwt;

import com.whatpl.account.AccountService;
import com.whatpl.security.config.SecurityConfig;
import com.whatpl.swagger.test.service.SwaggerTestService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfig.class)
class JwtControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @MockBean
    JwtService jwtService;

    @MockBean
    JwtProperties jwtProperties;
    
    @MockBean
    SwaggerTestService swaggerTestService;

    @Test
    @DisplayName("토큰 재발급 요청")
    void reIssue() throws Exception {
        // given
        JwtResponse jwtResponse = JwtResponse.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
        when(jwtService.reIssueToken(any()))
                .thenReturn(jwtResponse);

        // when
        mockMvc.perform(post("/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .content("refreshToken=Bearer testToken"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(jwtResponse.getAccessToken()))
                .andExpect(jsonPath("$.refreshToken").value(jwtResponse.getRefreshToken()));
    }
}