package com.umc.DongnaeFriend.domain.user.service;

import com.umc.DongnaeFriend.domain.type.Age;
import com.umc.DongnaeFriend.domain.type.Gender;
import com.umc.DongnaeFriend.domain.type.YesNo;
import com.umc.DongnaeFriend.domain.user.dto.UserDto;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    KakaoService kakaoService;

    public void userValidation(HashMap<String, Object> userInfo) {
        Optional<User> user= userRepository.findById((Long) userInfo.get("userId"));
        if (user.isEmpty()) {
            userRegister(userInfo);
        }
    }


    //유저 회원가입
    public void userRegister(HashMap<String, Object> userInfo) {
        //필수
        String nickName = userInfo.get("nickname").toString();
        //필수
        String email = userInfo.get("email").toString();

        Optional<String> gender = Optional.ofNullable(userInfo.get("gender").toString());
        Optional<String> age = Optional.ofNullable(userInfo.get("age").toString());



        userRepository.save(
                User.builder()
                        .nickname(nickName)
                        .email(email)
                        //TODO : Gender 결정
                        .gender(Gender.FEMALE)
                        //TODO : Age 결정
                        .age(Age.AGE10)
                        .townCert(YesNo.NO)
                        .townCertCnt(0)
                        .infoCert(YesNo.NO)
                        .build()
        );
    }
}
