package com.umc.DongnaeFriend.domain.dongnae.dto;



import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.type.DongnaeBoardCategory;
import lombok.*;

import java.util.List;

import static com.umc.DongnaeFriend.domain.type.DongnaeBoardCategory.*;


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

        public DongnaeBoard toEntity() {
            return DongnaeBoard.builder()
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
                    .build();
        }

    }


    @Getter @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ListResponse {
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



}

