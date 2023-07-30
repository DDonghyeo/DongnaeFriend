package com.umc.DongnaeFriend.domain.report.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeComment;
import com.umc.DongnaeFriend.domain.user.entity.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "report_user_id")
    private User reportUser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dongnae_board_id")
    private DongnaeBoard dongnaeBoard;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sharing_board_id")
    private SharingBoard sharingBoard;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dongnae_comment_id")
    private DongnaeComment dongnaeComment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sharing_comment_id")
    private SharingComment sharingComment;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;  //신고사유
}
