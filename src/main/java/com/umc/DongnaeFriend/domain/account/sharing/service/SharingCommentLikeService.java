package com.umc.DongnaeFriend.domain.account.sharing.service;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingCommentLike;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingCommentLikeRepository;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeCommentLike;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SharingCommentLikeService {
    private final SharingCommentLikeRepository sharingCommentLikeRepository;
    private final UserRepository userRepository;


    public String newLike(Long commentId) {

        User user = findUser();

        // 댓글 가져오기
        SharingComment sharingComment = sharingCommentLikeRepository.findByCommentId(commentId);

        // 좋아요 유무 확인하기
        SharingCommentLike sharingCommentLikeExist = sharingCommentLikeRepository.findByCommentId(sharingComment);

        if (sharingCommentLikeExist == null) {
            SharingCommentLike sharingCommentLike = SharingCommentLike.builder()
                    .user(user)
                    .sharingComment(sharingComment)
                    .build();

            sharingCommentLikeRepository.save(sharingCommentLike);

            return "가계부 공유 댓글 좋아요 성공";
        }

        sharingCommentLikeRepository.delete(sharingCommentLikeExist);

        return "가계부 공유 댓글 좋아요 삭제 성공";
    }

    public User findUser() {
        Object userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById((Long) userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
