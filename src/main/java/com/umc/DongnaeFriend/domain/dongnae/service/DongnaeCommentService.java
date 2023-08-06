package com.umc.DongnaeFriend.domain.dongnae.service;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeCommentDto;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeComment;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeCommentRepository;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DongnaeCommentService {
    private final DongnaeCommentRepository dongnaeCommentRepository;


    public String newComment(Long townInformationId, DongnaeCommentDto dongnaeCommentDto) {
        // !임시! 유저 가져오기
        User user = dongnaeCommentRepository.findByUserId(1L);

        // 게시판 가져오기
        DongnaeBoard dongnaeBoard = dongnaeCommentRepository.findByDongnaeBoardId(townInformationId);

        // 대댓글 등록
        if (!(dongnaeCommentDto.getParentCommentId() == null)){
            // 부모 댓글 가져오기
            Optional<DongnaeComment> parentCommentOptional = dongnaeCommentRepository.findById(dongnaeCommentDto.getParentCommentId());
            DongnaeComment parentComment = parentCommentOptional.get();

            // 댓글 빌드
            DongnaeComment comment = DongnaeComment.builder()
                    .parentComment(parentComment)
                    .content(dongnaeCommentDto.getContent())
                    .isDeleted(false)
                    .dongnaeBoard(dongnaeBoard)
                    .user(user)
                    .build();

            dongnaeCommentRepository.save(comment);

            return "대댓글 등록 성공";

        }

        // 댓글 빌드
        DongnaeComment comment = DongnaeComment.builder()
                .content(dongnaeCommentDto.getContent())
                .isDeleted(false)
                .dongnaeBoard(dongnaeBoard)
                .user(user)
                .build();

        dongnaeCommentRepository.save(comment);

        return "댓글 등록 성공";
    }
}
