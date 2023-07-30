package com.umc.DongnaeFriend.domain.profile.dto;

import com.umc.DongnaeFriend.domain.dongnae.dto.UserLocationDto;
import com.umc.DongnaeFriend.domain.type.Age;
import com.umc.DongnaeFriend.domain.type.Gender;
import com.umc.DongnaeFriend.domain.type.YesNo;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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

        @NotNull(message = "닉네임은 필수입니다.")
        private String nickname;

        @NotNull(message = "공개/비공개 여부를 결정해주세요.")
        private YesNo infoCert;

        public User toEntity(){
            return User.builder()
                    .nickname(nickname)
                    .infoCert(infoCert)
                    .build();
        }
    }
}
