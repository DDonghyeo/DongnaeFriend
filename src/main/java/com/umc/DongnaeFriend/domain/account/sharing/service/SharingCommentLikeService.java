package com.umc.DongnaeFriend.domain.account.sharing.service;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingCommentLike;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingCommentLikeRepository;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SharingCommentLikeService {
    private final SharingCommentLikeRepository sharingCommentLikeRepository;

    public String newLike(Long commentId) {
        // !임시! 유저 가져오기
        User user = sharingCommentLikeRepository.findByUserId(1L);

        // 댓글 가져오기
        SharingComment sharingComment = sharingCommentLikeRepository.findByCommentId(commentId);

        SharingCommentLike sharingCommentLike = SharingCommentLike.builder()
                .user(user)
                .sharingComment(sharingComment)
                .build();

        sharingCommentLikeRepository.save(sharingCommentLike);

        return "가계부 공유 댓글 좋아요 성공";
    }
}
