package com.umc.DongnaeFriend.domain.profile.dto;

import com.umc.DongnaeFriend.domain.dongnae.dto.UserLocationDto;
import com.umc.DongnaeFriend.domain.type.Age;
import com.umc.DongnaeFriend.domain.type.Gender;
import com.umc.DongnaeFriend.domain.type.YesNo;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MyPageDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPageResponseDto{
        private String nickname;
        private String profileImage;
        private String town;
        private Age age;
        private Gender gender;

        public static MyPageResponseDto of(User user, UserLocationDto userLocation){
            return new MyPageResponseDto(
                    user.getNickname(),
                    user.getProfileImage(),
                    userLocation.getTown(),
                    user.getAge(),
                    user.getGender());
        }
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPageRequestDto{
        private String nickname;
        private Age age;
        //private String profileImage;
        private Gender gender;
        private YesNo infoCert;

        public User toEntity(){
            return User.builder()
                    .nickname(nickname)
                    //.profileImage(profileImage)
                    .age(age)
                    .gender(gender)
                    .infoCert(infoCert)
                    .build();
        }
    }
}
