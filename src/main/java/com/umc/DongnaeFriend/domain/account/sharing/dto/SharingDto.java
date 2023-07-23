package com.umc.DongnaeFriend.domain.account.sharing.dto;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.Dongnae;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.type.DongnaeBoardCategory;
import com.umc.DongnaeFriend.domain.type.SharingCategory;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SharingDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private int category;

        private String title;

        private String content;

        private List<String> images;

        public SharingBoard toEntity(User user) {
            return SharingBoard.builder()
                    .user(user)
                    .category(SharingCategory.valueOf(category))
                    .title(title)
                    .content(content)
                    .view(0)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        String profileImage;
        String nickname;
        int category;
        String title;
        String content;
        List<String> images;
        String createdAt;
        boolean isWriter;
        boolean likeOrNot;
        boolean scrapOrNot;
        int view;
    }




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
