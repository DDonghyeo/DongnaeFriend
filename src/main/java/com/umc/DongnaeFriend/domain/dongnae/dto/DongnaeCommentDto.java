package com.umc.DongnaeFriend.domain.dongnae.dto;

import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DongnaeCommentDto {
    String content;

    public static DongnaeCommentDto from(DongnaeComment dongnaeComment) {
        return new DongnaeCommentDto(
                dongnaeComment.getContent()
        );
    }
    @Getter
    public static class CommentListResponse {
        private List<DongnaeCommentDto> list;

        public CommentListResponse(List<DongnaeCommentDto> list) {
            this.list = list;
        }

        public static CommentListResponse of(List<DongnaeComment> list) {
            List<DongnaeCommentDto> dongnaeCommentDtos = list
                    .stream()
                    .map(DongnaeCommentDto::from)
                    .collect(Collectors.toList());

            return new CommentListResponse(dongnaeCommentDtos);
        }
    }

    @Getter @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ListResponse {
        private String nickname;

        private String content;

        private String createdAt;

        private int likes;
    }
}
