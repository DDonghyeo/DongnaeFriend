package com.umc.DongnaeFriend.domain.profile.dto;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeBoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


public class DongnaeProfileDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DongnaeProfileResponse{

        private Long userId;
        private boolean isMine;
        private String nickname;
        private String profileImage;
        private int postTotalCount;
        private int commentTotalCount;
        private int likedTotalCount;
        private UserProfileDto.UserProfileResponseDto profile;
        private List<DongnaeBoardDto.DongnaeProfileListResponse> content;
    }
}
