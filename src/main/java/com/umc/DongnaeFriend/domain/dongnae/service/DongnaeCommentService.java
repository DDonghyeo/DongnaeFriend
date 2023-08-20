package com.umc.DongnaeFriend.domain.dongnae.service;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingSympathy;
import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeCommentDto;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeComment;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeCommentLike;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeCommentLikeRepository;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeCommentRepository;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DongnaeCommentService {
    private final DongnaeCommentRepository dongnaeCommentRepository;
    private final DongnaeCommentLikeRepository dongnaeCommentLikeRepository;


    public String newComment(Long townInformationId, DongnaeCommentDto dongnaeCommentDto) {
        // !임시! 유저 가져오기
        User user = dongnaeCommentRepository.findByUserId(1L);

        // 게시판 가져오기
        DongnaeBoard dongnaeBoard = dongnaeCommentRepository.findByDongnaeBoardId(townInformationId);

//        // 대댓글 등록
//        if (!(dongnaeCommentDto.getParentCommentId() == null)){
//            // 부모 댓글 가져오기
//            Optional<DongnaeComment> parentCommentOptional = dongnaeCommentRepository.findById(dongnaeCommentDto.getParentCommentId());
//            DongnaeComment parentComment = parentCommentOptional.get();
//
//            // 댓글 빌드
//            DongnaeComment comment = DongnaeComment.builder()
//                    .parentComment(parentComment)
//                    .content(dongnaeCommentDto.getContent())
//                    .isDeleted(false)
//                    .dongnaeBoard(dongnaeBoard)
//                    .user(user)
//                    .build();
//
//            dongnaeCommentRepository.save(comment);
//
//            return "대댓글 등록 성공";
//
//        }

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

    // [동네정보] 댓글 수정
    public String modifyComment(Long commentId, DongnaeCommentDto dongnaeCommentDto) {
        // 댓글 찾기
        Optional<DongnaeComment> dongnaeCommentOptional = dongnaeCommentRepository.findById(commentId);
        DongnaeComment dongnaeComment = dongnaeCommentOptional.get();

        dongnaeComment.modifyComment(dongnaeCommentDto);

        dongnaeCommentRepository.save(dongnaeComment);

        return "댓글 수정 성공";
    }

    public String deleteComment(Long commentId) {
        // 댓글 찾기
        Optional<DongnaeComment> dongnaeCommentOptional = dongnaeCommentRepository.findById(commentId);
        DongnaeComment dongnaeComment = dongnaeCommentOptional.get();

        dongnaeCommentRepository.delete(dongnaeComment);

        return "댓글 삭제 성공";
    }

    public String newLike(Long commentId) {
        // !임시! 유저 가져오기
        User user = dongnaeCommentRepository.findByUserId(1L);

        // 댓글 가져오기
        Optional<DongnaeComment> dongnaeCommentOptional = dongnaeCommentRepository.findById(commentId);
        DongnaeComment dongnaeComment = dongnaeCommentOptional.get();

        // 좋아요 유무 확인하기
        DongnaeCommentLike dongnaeCommentLikeExist = dongnaeCommentLikeRepository.findByCommentId(dongnaeComment);

        if (dongnaeCommentLikeExist == null) {
            DongnaeCommentLike dongnaeCommentLike = DongnaeCommentLike.builder()
                    .user(user)
                    .dongnaeComment(dongnaeComment)
                    .build();

            dongnaeCommentLikeRepository.save(dongnaeCommentLike);

            return "동네정보 댓글 좋아요 성공";
        }

        dongnaeCommentLikeRepository.delete(dongnaeCommentLikeExist);

        return "동네정보 댓글 좋아요 삭제 성공";
    }

    public DongnaeCommentDto.CommentListResponse getList(Long id) {

        // 게시판 가져오기
        DongnaeBoard dongnaeBoard = dongnaeCommentRepository.findByDongnaeBoardId(id);

        List<DongnaeComment> list = dongnaeCommentRepository.findListByBoardId(dongnaeBoard);
        return DongnaeCommentDto.CommentListResponse.of(list);

    }
}
