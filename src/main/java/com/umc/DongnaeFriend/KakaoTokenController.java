package com.umc.DongnaeFriend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.DongnaeFriend.domain.user.dto.UserDto;
import com.umc.DongnaeFriend.domain.user.service.KakaoService;
import com.umc.DongnaeFriend.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("")
public class KakaoTokenController {

    @Autowired
    private UserService userService;

    @Autowired
    private KakaoService kakaoService;

    @GetMapping("/kakao")
    public String kakologin(Model model, HttpServletResponse response) {
        response.setContentType(MediaType.TEXT_HTML_VALUE);

        return "html/index";
    }

    @GetMapping("/callback")
    public String callback(Model model, @RequestParam("code") String code) throws IOException {

        try {
//------kakao POST 요청------
            String reqURL = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=1ad317e194df665ca44dcb82d11a7093&code=" + code;
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

            String accessToken = (String) jsonMap.get("access_token");

            //-------------------------------------------------서버 로그인----------------------------------------------------

            HashMap<String, Object> userInfo = kakaoService.getUserInfo(accessToken);
            UserDto.Response response =  userService.userValidation(userInfo);

            model.addAttribute("token","Bearer "+ response.getAccessToken());

            return "html/token";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
