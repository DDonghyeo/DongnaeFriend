package com.umc.DongnaeFriend.domain.account.sharing.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.umc.DongnaeFriend.domain.BaseTimeEntity;
import com.umc.DongnaeFriend.domain.account.sharing.dto.ReqSharingCommentDto;
import com.umc.DongnaeFriend.domain.user.entity.User;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity
public class SharingComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "sharing_comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sharing_board_id", nullable = false)
    private SharingBoard sharingBoard;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    private SharingComment parentComment;  //대댓글일 경우, 부모 댓글 id를 가진다.

    @OneToMany(mappedBy = "parentComment")
    private List<SharingComment> childComment;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(nullable = false)
    private String content;

    public void modifyComment(ReqSharingCommentDto reqSharingCommentDto) {
        this.content = reqSharingCommentDto.getContent();
    }
}
