package com.umc.DongnaeFriend.domain.report.dto;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeComment;
import com.umc.DongnaeFriend.domain.report.entity.Report;
import com.umc.DongnaeFriend.domain.user.entity.User;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportDto {

    @Getter
    @NoArgsConstructor
    public static class ReportRequest {

        private Long reportUserId;
        private Long sharingBoardId;
        private Long dongnaeBoardId;
        private Long sharingCommentId;
        private Long dongnaeCommentId;

        @NotBlank(message = "신고사유는 필수 값입니다.")
        private String content;

        public Report toEntity(User user, User reportUser, DongnaeBoard db, SharingBoard sb,
            DongnaeComment dc, SharingComment sc, String content) {
            return Report.builder()
                .user(user)
                .reportUser(reportUser)
                .dongnaeBoard(db)
                .sharingBoard(sb)
                .dongnaeComment(dc)
                .sharingComment(sc)
                .content(content)
                .build();
        }
    }
}
