package com.umc.DongnaeFriend.domain.user.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.DongnaeFriend.domain.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class KakaoServiceimpl implements KakaoService {

//    @Autowired
//    public IACDao dao;

    @Autowired
    private UserService userService;

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> getUserInfo(String access_Token) throws IOException {
        // 클라이언트 요청 정보
        HashMap<String, Object> userInfo = new HashMap<String, Object>();


        //------kakao GET 요청------
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + access_Token);

        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }
        System.out.println("response body : " + result);
        System.out.println("result type" + result.getClass().getName()); // java.lang.String

        // jackson objectmapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        // JSON String -> Map
        Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
        });


        System.out.println(jsonMap.get("properties"));

        Long id = (Long) jsonMap.get("id");
        Map<String, Object> properties = (Map<String, Object>) jsonMap.get("properties");
        Map<String, Object> kakao_account = (Map<String, Object>) jsonMap.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");

        log.info("profile : " + profile.toString());
        log.info("kakao_acount : " + kakao_account.toString());

        String nickname = properties.get("nickname").toString();
        String profileImage = properties.get("profile_image").toString();
        String email = kakao_account.get("email").toString();
        String gender = kakao_account.getOrDefault("gender","").toString();
        String age = kakao_account.getOrDefault("age_range","").toString();

        userInfo.put("id", id);
        userInfo.put("nickname", nickname);
        userInfo.put("profileImage", profileImage);
        userInfo.put("email", email);
        userInfo.put("age", age);
        userInfo.put("gender", gender);

        return userInfo;
    }

    @Override
    public String getAccessTokenFromKakao(String client_id, String code) throws IOException {

        //------kakao POST 요청------
        String reqURL = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=" + client_id + "&code=" + code;
        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
        });

        log.info("Response Bosy : " + result);

        String accessToken = (String) jsonMap.get("access_token");


        return accessToken;
    }

}