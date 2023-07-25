package com.umc.DongnaeFriend.domain.profile.dto;

import com.umc.DongnaeFriend.domain.dongnae.dto.UserLocationDto;
import com.umc.DongnaeFriend.domain.type.Age;
import com.umc.DongnaeFriend.domain.type.Gender;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserProfileDto{
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserProfileResponseDto {
        private String nickname;
        private String profileImage;
        private String town;
        private Age age;
        private Gender gender;

        public static UserProfileDto.UserProfileResponseDto of(User user) {
            return new UserProfileDto.UserProfileResponseDto(
                    user.getNickname(),
                    user.getProfileImage(),
                    user.getDongnae().getTownName(),
                    user.getAge(),
                    user.getGender());
        }
    }
}
