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
    @PutMapping("/{comment_id}")
    public String putComment(@PathVariable("comment_id") Long comment_id,  @RequestBody ReqSharingCommentDto reqSharingCommentDto) {
        sharingCommentService.modifyComment(comment_id, reqSharingCommentDto);
        return "";
    }
}
