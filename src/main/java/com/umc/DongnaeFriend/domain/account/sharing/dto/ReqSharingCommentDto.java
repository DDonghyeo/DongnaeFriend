package com.umc.DongnaeFriend.domain.account.sharing.dto;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqSharingCommentDto {
    String content;

    public static ReqSharingCommentDto from(SharingComment sharingComment) {
        return new ReqSharingCommentDto(
                sharingComment.getContent()
        );
    }

    @Getter
    public static class CommentListResponse {
        private List<ReqSharingCommentDto> list;

        public CommentListResponse(List<ReqSharingCommentDto> list) {
            this.list = list;
        }

        public static CommentListResponse of(List<SharingComment> list) {
            List<ReqSharingCommentDto> sharingCommentDtos = list
                    .stream()
                    .map(ReqSharingCommentDto::from)
                    .collect(Collectors.toList());

            return new CommentListResponse(sharingCommentDtos);
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
