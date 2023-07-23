package com.umc.DongnaeFriend.domain.profile.service;

import com.umc.DongnaeFriend.domain.dongnae.dto.UserLocationDto;
import com.umc.DongnaeFriend.domain.dongnae.entity.Dongnae;
import com.umc.DongnaeFriend.domain.profile.dto.MyPageDto;
import com.umc.DongnaeFriend.domain.type.Age;
import com.umc.DongnaeFriend.domain.type.Gender;
import com.umc.DongnaeFriend.domain.type.YesNo;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {

    /**
     * 사용자 정보 확인 필요 !
     */

    //임시 유저 & 동네
    Dongnae dongnae = Dongnae.builder().id(1L).gu("서울구").dong("서울동").city("서울시").townName("무슨마을").build();
    User user = User.builder().age(Age.AGE10).profileImage("profileImg").email("email").dongnae(dongnae).gender(Gender.FEMALE).infoCert(YesNo.NO).townCert(YesNo.NO).id(1L).kakaoId(90L).nickname("nickname").refreshToken("refreshToken").build();

    private final UserRepository userRepository;

    public MyPageDto.MyPageResponseDto getMyPage(){
        //User user = userRepository.findById(userId).orElseThrow();
        return MyPageDto.MyPageResponseDto.of(user, getUserLocation());
    }

    public void updateMyPage(MyPageDto.MyPageRequestDto myPageRequest){
        //User user = userRepository.findById(userId).orElseThrow();
        User user = User.builder().age(Age.AGE10).profileImage("profileImg").email("email").dongnae(dongnae).gender(Gender.FEMALE).infoCert(YesNo.NO).townCert(YesNo.NO).id(1L).kakaoId(90L).nickname("nickname").refreshToken("refreshToken").build();

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
}
