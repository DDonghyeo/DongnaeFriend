package com.umc.DongnaeFriend.domain.user.service;


import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.HashMap;

public interface KakaoService {


    @SuppressWarnings("unchecked")
    HashMap<String, Object> getUserInfo(String access_Token) throws IOException;
}


