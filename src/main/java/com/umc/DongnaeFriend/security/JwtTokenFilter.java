package com.umc.DongnaeFriend.security;

import com.umc.DongnaeFriend.global.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Request Header에서 JWT 토큰 가져오기
        String authorizationHeader = request.getHeader("Authorization");

        // JWT 토큰이 "Bearer "로 시작하는지 확인하고 토큰 추출
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                // JWT 토큰 검증
                JwtUtil.validateToken(token);

                // JWT 토큰에서 사용자 정보 추출 (예: 사용자 ID)
                Long userId = JwtUtil.getUserIdFromToken(token);

                // 인증 객체 생성
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userId, null, null);

                // SecurityContextHolder에 인증 객체 저장
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                // JWT 토큰 검증 실패 시, 인증 객체를 null로 설정
                SecurityContextHolder.clearContext();
            }
        }

    }
}
