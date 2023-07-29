package com.umc.DongnaeFriend.domain.account.sharing.controller;

import com.umc.DongnaeFriend.domain.account.sharing.dto.ReqSharingCommentDto;
import com.umc.DongnaeFriend.domain.account.sharing.service.SharingCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

}
