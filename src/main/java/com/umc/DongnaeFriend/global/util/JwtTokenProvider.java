package com.umc.DongnaeFriend.global.util;

import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.umc.DongnaeFriend.config.JwtConfig.SECRET_KEY;

@Log4j2
@Component
public class JwtTokenProvider {

    @Autowired
    private UserRepository userRepository;

    public JwtTokenProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 14; //2WEEK
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 30; //30DAY


    //accessToken 생성
    public String createAccessToken(Long userId) {
        Date now = new Date(); //현재 시간
        Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH);


//        CustomAuthentication user = (CustomAuthentication) authentication.getPrincipal();
//
//        Claims claims = Jwts.claims().setSubject(user.getUsername());
//        claims.put("userId", user.getId()); // 사용자 아이디
//        claims.put("email", user.getEmail()); // 사용자 이메일

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, String.valueOf(SECRET_KEY))
                .claim("userId", userId)
                .setIssuedAt(now) //token 발행 시간
                .setExpiration(validity)
                .compact();
    }





}