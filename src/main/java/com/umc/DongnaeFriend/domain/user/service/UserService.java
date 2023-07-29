package com.umc.DongnaeFriend.domain.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.DongnaeFriend.domain.type.Age;
import com.umc.DongnaeFriend.domain.type.Gender;
import com.umc.DongnaeFriend.domain.type.YesNo;
import com.umc.DongnaeFriend.domain.user.dto.UserDto;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import com.umc.DongnaeFriend.global.util.JwtTokenProvider;
import com.umc.DongnaeFriend.global.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    KakaoService kakaoService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    public UserDto.Response userValidation(HashMap<String, Object> userInfo) {
        Long kakao_id = (Long) userInfo.get("id");
        Optional<User> user= userRepository.findByKakaoId(kakao_id);
        if (user.isEmpty()) {
            User new_user = userRegister(userInfo);
            return UserDto.Response.builder()
                    .accessToken(jwtTokenProvider.createAccessToken(new_user.getId()))
                    .refreshToken(new_user.getRefreshToken())
                    .build();
        } else {
            return UserDto.Response.builder()
                    .accessToken(jwtTokenProvider.createAccessToken(user.get().getId()))
                    .refreshToken(user.get().getRefreshToken())
                    .build();
        }

    }


    //유저 회원가입 -> Refresh Token을 return
    public User userRegister(HashMap<String, Object> userInfo) {
        //필수
        String nickName = userInfo.get("nickname").toString();
        //필수
        String email = userInfo.get("email").toString();

        String profileImage = userInfo.get("profileImage").toString();

        Long kakaoId = (Long) userInfo.get("id");

        String strGender =  userInfo.getOrDefault("gender", "").toString();
        String strAge = userInfo.getOrDefault("age", "").toString();

        Gender gender = Gender.fromString(strGender);
        Age age = Age.fromString(strAge);


        String refresh_Token = jwtTokenProvider.createRefreshToken((Long) userInfo.get("id"));

        return userRepository.save(
                User.builder()
                        .nickname(nickName)
//                        .dongnae(
//
//                        )
                        .email(email)
                        .gender(gender)
                        .age(age)
                        .townCert(YesNo.NO)
                        .townCertCnt(0)
                        .infoCert(YesNo.NO)
                        .profileImage(profileImage)
                        .kakaoId(kakaoId)
                        .refreshToken(refresh_Token)
                        .build()
        );
    }

    // RefreshToken으로 AccessToken 재발급
    public String createAccessTokenFromRefreshToken(String refreshToken) {
        String accessToken = "";

        // 전달받은 RefreshToken 정보로 사용자 조회(유효하지 않은 토큰일 시, 예외 발생)
        Optional<User> userByRefreshToken =Optional.ofNullable(userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new CustomException(ErrorCode.INVALID_REFRESH_TOKEN,"해당 refreshToken 이 존재하지 않음")));

        // AccessToken 재발행
        accessToken = jwtTokenProvider.createAccessToken(userByRefreshToken.get().getId());
        log.info("AcessToken 재발행 성공");

        return accessToken;
    }

    public String kakaoGetCode() {


        try {
            String reqURL= "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=1ad317e194df665ca44dcb82d11a7093&redirect_uri=http://localhost:8080/callback";

            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");


            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, Object> jsonMap = objectMapper.readValue(result, HashMap.class);

            log.info(jsonMap.toString());
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
