package com.umc.DongnaeFriend.domain.profile.service;

import com.umc.DongnaeFriend.domain.dongnae.dto.UserLocationDto;
import com.umc.DongnaeFriend.domain.dongnae.entity.Dongnae;
import com.umc.DongnaeFriend.domain.profile.dto.MyPageDto;
import com.umc.DongnaeFriend.domain.type.Age;
import com.umc.DongnaeFriend.domain.type.Gender;
import com.umc.DongnaeFriend.domain.type.YesNo;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import com.umc.DongnaeFriend.global.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    public MyPageDto.MyPageResponseDto getMyPage(){
        User user = findUser();
        return MyPageDto.MyPageResponseDto.of(user, getUserLocation());
    }

    public void updateMyPage(MyPageDto.MyPageRequestDto myPageRequest, MultipartFile image){

        User user = findUser();
        String fileName = "ProfileImage_" + user.getId().toString()+".png";

        log.info("fileName : " + fileName);

        try {
            log.info("fileUpload 시작합니당");
            log.info("image : " + image.isEmpty());
            log.info("filename : " + fileName);
            FileUtil.fileUpload(image, fileName);

        }catch (IOException e){
            throw new RuntimeException(e);
        }
        user.updateProfileImage(fileName);
        log.info("프로필 이미지 업데이트완료");
        user.updateProfile(myPageRequest.toEntity());

        log.info("유저 닉네임 : " + user.getNickname());
        log.info("유저 프로필 : " + user.getProfileImage());
        log.info("유저 age : " + user.getAge());
        log.info("유저 gender : " + user.getGender());
        log.info("유저 infocert : " + user.getInfoCert());
    }
    public UserLocationDto getUserLocation(){
        return new UserLocationDto("한남동");
    }

    public User findUser(){
        Object userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById((Long)userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
