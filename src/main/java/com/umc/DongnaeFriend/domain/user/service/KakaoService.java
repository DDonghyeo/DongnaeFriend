package com.umc.DongnaeFriend.domain.user.service;


import com.umc.DongnaeFriend.domain.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.HashMap;

public interface KakaoService {


    @SuppressWarnings("unchecked")
    HashMap<String, Object> getUserInfo(String access_Token) throws IOException;
    String getAccessTokenFromKakao(String client_id, String code) throws IOException;
}


