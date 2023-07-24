package com.umc.DongnaeFriend.domain.user.contorller;

import com.umc.DongnaeFriend.domain.user.dto.UserDto;
import com.umc.DongnaeFriend.domain.user.service.KakaoService;
import com.umc.DongnaeFriend.domain.user.service.UserService;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import com.umc.DongnaeFriend.global.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    KakaoService kakaoService;

    @Autowired
    UserService userService;

    JwtTokenProvider jwtTokenProvider;

    /**
     * 유저 로그인 / 회원가입
     * 인증 절차
     */
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody UserDto.Request request) {
        try {
            //사용자 정보 가져오기
            HashMap<String, Object> userInfo = kakaoService.getUserInfo(request.getAccessToken());

            //사용자 확인  기존 회원 -> 넘어가고, 없는 회원 -> 회원가입
            userService.userValidation(userInfo);

            //토큰 생성
            String access_token = jwtTokenProvider.createAccessToken((Long) userInfo.get("usreId"));

            return ResponseEntity.ok(access_token);

        } catch (IOException e) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }
    }

    @PostMapping("/user/reissuance")
    public ResponseEntity<?> reiussnaceToken(String access_oto)



}
