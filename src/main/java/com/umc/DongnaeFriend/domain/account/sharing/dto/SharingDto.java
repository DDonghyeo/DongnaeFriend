package com.umc.DongnaeFriend.domain.account.sharing.dto;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.type.SharingCategory;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SharingDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        @NotNull(message = "카테고리는 필수입니다.")
        private int category;

        @NotNull(message = "제목은 필수입니다.")
        private String title;

        @NotNull(message = "내용은 필수입니다.")
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

    /**
     * 프로필 조회 시 필요한 정보
     */
    @Getter @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountBookProfileListResponse {
        private Long boardId;
        private int category;
        private String title;
        private String imageUrl;
        //private String town;
        private String createdAt;
        private int commentCount;
        private int likeCount;
    }

}
