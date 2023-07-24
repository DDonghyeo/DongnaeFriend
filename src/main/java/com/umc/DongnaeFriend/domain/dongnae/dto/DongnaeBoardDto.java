package com.umc.DongnaeFriend.domain.dongnae.dto;



import com.umc.DongnaeFriend.domain.dongnae.entity.Dongnae;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.type.DongnaeBoardCategory;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.*;

import java.util.List;


public class DongnaeBoardDto {

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private int category;
        private String title;
        private String content;
        private List<String> images;
        private String place;

        private String placeLocation;

        public DongnaeBoard toEntity(User user, Dongnae dongnae) {
            return DongnaeBoard.builder()
                    .user(user)
                    .category(DongnaeBoardCategory.valueOf(category)
//                            category == 0 ? RESTAURANT:
//                            category == 1 ? FACILITY:
//                            category == 2 ? SHARE_INFORMATION:
//                            category == 3 ? TOGETHER:
//                            category == 4 ? COMMUNICATION:
//                            category == 5 ? ETC : null
                    )
                    .title(title)
                    .content(content)
                    .place(place)
                    .placeLocation(placeLocation)

                    .dongnae(dongnae)
                    .view(0)
                    .build();
        }

    }


    @Getter @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ListResponse {
        private Long id;
        private String town;

        private int category;

        private String title;

        private String content;

        private String imageUrl;

        private String createdAt;

        private int view;

        private int commentCount;

        private int likes;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String profileImage;

        private String nickname;

        private int category;

        private String title;

        private String content;

        private List<String> images;

        private String place;

        private String placeLocation;

        private String createdAt;

        private int townCertification;

        //게시글 조회한 사람이 작성자인지?
        private boolean isWriter;

        private boolean likeOrNot;

        private boolean scrapOrNot;

        private int view;

    }


}

