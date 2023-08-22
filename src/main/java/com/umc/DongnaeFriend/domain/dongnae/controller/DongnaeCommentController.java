package com.umc.DongnaeFriend.domain.dongnae.controller;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeCommentDto;
import com.umc.DongnaeFriend.domain.dongnae.service.DongnaeCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/town-information/comments")
public class DongnaeCommentController {
    private final DongnaeCommentService dongnaeCommentService;

    // [동네정보] 댓글 등록
    @PostMapping("/{townInformationId}")
    public String postComment(@PathVariable("townInformationId") Long townInformationId, @RequestBody DongnaeCommentDto dongnaeCommentDto) {
        dongnaeCommentService.newComment(townInformationId, dongnaeCommentDto);
        return "";
    }

    // [동네정보] 댓글 수정
    @PutMapping("/{commentId}")
    public String putComment(@PathVariable("commentId") Long commentId,  @RequestBody DongnaeCommentDto dongnaeCommentDto) {
        dongnaeCommentService.modifyComment(commentId, dongnaeCommentDto);
        return "";
    }

    // [동네정보] 댓글 삭제
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId) {
        dongnaeCommentService.deleteComment(commentId);
        return "";
    }

    // [동네정보] 댓글 좋아요
    @PostMapping("/likes/{commentId}")
    public String postCommentLike(@PathVariable("commentId") Long commentId) {
        dongnaeCommentService.newLike(commentId);
        return "";
    }

    // [동네정보] 댓글 목록 조회
    @GetMapping("")
    public ResponseEntity<?> getList(@RequestParam Long townInformationId) {
        return ResponseEntity.ok(dongnaeCommentService.getList(townInformationId));
    }
}
