package com.umc.DongnaeFriend.domain.user.service;


import java.io.IOException;
import java.util.HashMap;

public interface KakaoService {

    HashMap<String, Object> getUserInfo(String access_Token) throws IOException;
}


