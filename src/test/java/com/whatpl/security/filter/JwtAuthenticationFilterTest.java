package com.whatpl.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatpl.account.AccountService;
import com.whatpl.jwt.JwtProperties;
import com.whatpl.jwt.JwtService;
import com.whatpl.security.config.SecurityConfig;
import com.whatpl.security.domain.AccountPrincipal;
import com.whatpl.swagger.test.service.SwaggerTestService;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
@Import(SecurityConfig.class)
class JwtAuthenticationFilterTest {

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
    @DisplayName("Authorization 헤더의 토큰이 유효할 경우 사용자 인증")
    void doFilterInternal() throws Exception {
        // given: 토큰이 유효한 경우로 세팅
        String tokenType = "Bearer";
        String validToken = "validToken";
        var principal = new AccountPrincipal(1L, "test", "", Collections.emptySet(), null);
        var authenticationToken = new UsernamePasswordAuthenticationToken(principal, "", Collections.emptySet());
        when(jwtProperties.getTokenType())
                .thenReturn(tokenType);
        when(jwtService.resolveToken(any()))
                .thenReturn(authenticationToken);

        // expected
        mockMvc.perform(get("/")
                        .header(HttpHeaders.AUTHORIZATION, tokenType + validToken))
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andDo(print());
    }

    @DisplayName("Authorization 헤더에 값이 없거나 지정된 TokenType 으로 시작하지 않으면 다음 필터 수행")
    @ParameterizedTest
    @ValueSource(strings = {"", "Bearer", "Basic", "Basic testToken"})
    void shouldNotFilter(String tokenType) throws ServletException {
        // given
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(new ObjectMapper(),
                jwtService, jwtProperties, null);
        MockHttpServletRequest request = new MockHttpServletRequest(HttpMethod.GET.name(), "/");
        request.addHeader(HttpHeaders.AUTHORIZATION, tokenType);
        when(jwtProperties.getTokenType())
                .thenReturn("Bearer");

        // when
        boolean shouldNotFilter = jwtAuthenticationFilter.shouldNotFilter(request);

        // then
        assertTrue(shouldNotFilter);
    }

    @Test
    @DisplayName("토큰 재발급 요청일 경우 다음 필터 수행")
    void shouldNotFilter2() throws ServletException {
        // given
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(new ObjectMapper(),
                jwtService, jwtProperties, null);
        MockHttpServletRequest request = new MockHttpServletRequest(HttpMethod.POST.name(), "/token");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer testToken");
        when(jwtProperties.getTokenType())
                .thenReturn("Bearer");

        // when
        boolean shouldNotFilter = jwtAuthenticationFilter.shouldNotFilter(request);

        // then
        assertTrue(shouldNotFilter);
    }
}