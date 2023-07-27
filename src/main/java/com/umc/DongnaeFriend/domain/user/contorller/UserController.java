package com.umc.DongnaeFriend.domain.user.contorller;

import com.umc.DongnaeFriend.domain.user.dto.UserDto;
import com.umc.DongnaeFriend.domain.user.service.KakaoService;
import com.umc.DongnaeFriend.domain.user.service.UserService;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import com.umc.DongnaeFriend.global.util.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    KakaoService kakaoService;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;



    /**
     * 유저 로그인 / 회원가입
     * 인증 절차
     */
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestParam("accessToken") String accessToken, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        log.info("LoginController 진입");

//        if (!type.equals("kakao")) {
//            throw new CustomException(ErrorCode.SERVER_ERROR);
//        }


        try {
            log.info("userLogin 진입");
            //사용자 정보 가져오기
            HashMap<String, Object> userInfo = kakaoService.getUserInfo(accessToken);

            //사용자 확인  기존 회원 -> 넘어가고, 없는 회원 -> 회원가입

            UserDto.Response response =  userService.userValidation(userInfo);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }
    }

    @PostMapping("/user/reissuance")
    public ResponseEntity<?> reiussnaceToken(String refreshToken) {
        try {

            //토큰 재발급
            String access_token = userService.createAccessTokenFromRefreshToken(refreshToken);
            return ResponseEntity.ok(access_token);
        } catch (Exception e) {
            // RefreshToken만료
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }



}
