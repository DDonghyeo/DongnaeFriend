package com.umc.DongnaeFriend.domain.account.sharing.controller;

import com.umc.DongnaeFriend.domain.account.sharing.dto.ReqSharingCommentDto;
import com.umc.DongnaeFriend.domain.account.sharing.dto.ResSharingCommentList;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import com.umc.DongnaeFriend.domain.account.sharing.service.SharingCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account-books/sharing/comments")
public class SharingCommentController {
    private final SharingCommentService sharingCommentService;

    // [가계부 공유] 댓글 등록
    @PostMapping("/{accountBookId}")
    public String postComment(@PathVariable("accountBookId") Long accountBookId,  @RequestBody ReqSharingCommentDto reqSharingCommentDto) {
        sharingCommentService.newComment(accountBookId, reqSharingCommentDto);
        return "";
    }

    // [가계부 공유] 댓글 수정
    @PutMapping("/{commentId}")
    public String putComment(@PathVariable("commentId") Long commentId,  @RequestBody ReqSharingCommentDto reqSharingCommentDto) {
        sharingCommentService.modifyComment(commentId, reqSharingCommentDto);
        return "";
    }

    // [가계부 공유] 댓글 삭제
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId) {
        sharingCommentService.deleteComment(commentId);
        return "";
    }

    // [가계부 공유] 댓글 목록 조회
    @GetMapping("")
    public ResSharingCommentList getList(@RequestParam Long accountBookId) {
        ResSharingCommentList resSharingCommentList = sharingCommentService.getCommentList(accountBookId);
        return resSharingCommentList;
    }
}
