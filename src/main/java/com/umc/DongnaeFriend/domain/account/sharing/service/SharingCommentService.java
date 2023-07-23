package com.umc.DongnaeFriend.domain.account.sharing.service;

import com.umc.DongnaeFriend.domain.account.sharing.dto.ReqSharingCommentDto;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingCommentRepository;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SharingCommentService {
    private final SharingCommentRepository sharingCommentRepository;

    public String newComment(Long accountBookId, ReqSharingCommentDto reqSharingCommentDto) {
        // !임시! 유저 가져오기
        User user = sharingCommentRepository.findByUserId(1L);

        // 게시판 가져오기
        SharingBoard sharingBoard = sharingCommentRepository.findBySharingBoardId(accountBookId);

        // 대댓글 등록
        if (!(reqSharingCommentDto.getParentCommentId() == null)){
            // 부모 댓글 가져오기
            Optional<SharingComment> parentCommentOptional = sharingCommentRepository.findById(reqSharingCommentDto.getParentCommentId());
            SharingComment parentComment = parentCommentOptional.get();

            // 댓글 빌드
            SharingComment comment = SharingComment.builder()
                    .parentComment(parentComment)
                    .content(reqSharingCommentDto.getContent())
                    .isDeleted(false)
                    .sharingBoard(sharingBoard)
                    .user(user)
                    .build();

            sharingCommentRepository.save(comment);

            return "대댓글 등록 성공";

        }

        // 댓글 등록
        SharingComment comment = SharingComment.builder()
                .content(reqSharingCommentDto.getContent())
                .isDeleted(false)
                .sharingBoard(sharingBoard)
                .user(user)
                .build();

        sharingCommentRepository.save(comment);

        return "댓글 등록 성공";
    }

    public String modifyComment(Long comment_id, ReqSharingCommentDto reqSharingCommentDto) {
        // 댓글 찾기
        Optional<SharingComment> sharingCommentOptional = sharingCommentRepository.findById(comment_id);
        SharingComment sharingComment = sharingCommentOptional.get();

        sharingComment.modifyComment(reqSharingCommentDto);

        sharingCommentRepository.save(sharingComment);

        return "댓글 수정 성공";
    }
}
