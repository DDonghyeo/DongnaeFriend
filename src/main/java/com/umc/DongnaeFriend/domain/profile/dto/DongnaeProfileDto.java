package com.umc.DongnaeFriend.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DongnaeProfileDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DongnaeProfileResponse{
        private int postTotalCount;
        private int commentTotalCount;
        private int likedTotalCount;
    }
}
