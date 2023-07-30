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
}
