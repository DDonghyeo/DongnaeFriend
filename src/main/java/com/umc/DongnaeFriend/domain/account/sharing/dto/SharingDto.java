package com.umc.DongnaeFriend.domain.account.sharing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SharingDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ListResponse {
        private Long id;

        private int category;

        private String title;

        private String content;

        private String imageUrl;

        private String createdAt;

        private int view;

        private int commentCount;

        private int likes;

    }



}
