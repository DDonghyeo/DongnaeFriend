package com.umc.DongnaeFriend.global.security;

import com.umc.DongnaeFriend.global.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    public JwtTokenFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("JwtTokenFilter 진입");

        if (request.getServletPath().contains("/user/login")) {
            log.info("/user/login 진입");
        }

        // Request Header에서 JWT 토큰 가져오기
        String authorizationHeader = request.getHeader("Authorization");
        log.info("authorizationHeader : {}",authorizationHeader);

        //🛑 첫 로그인 시에도 이곳에서 걸리기 때문에 로그인이 안됨.(null)
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            log.info("token : {}", token);
            try {
                // JWT 토큰 검증
                jwtUtil.validateToken(token);
                log.info("JWT 토큰 검증완료");

                // JWT 토큰에서 사용자 정보 추출 (예: 사용자 ID)
                Long userId = jwtUtil.getUserIdFromToken(token);

                // 인증 객체 생성
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken( userId,null, null);

                // SecurityContextHolder에 인증 객체 저장

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request, response);


            } catch (Exception e) {
                e.printStackTrace();

                // JWT 토큰 검증 실패 시, 인증 객체를 null로 설정
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
            }
        } else {
            log.info("Header None");
            filterChain.doFilter(request, response);
        }

    }
}