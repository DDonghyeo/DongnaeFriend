package com.umc.DongnaeFriend.domain.account.sharing.controller;

import com.umc.DongnaeFriend.domain.account.sharing.dto.ReqSharingCommentDto;
import com.umc.DongnaeFriend.domain.account.sharing.service.SharingCommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account-books/sharing/comments/likes")
public class SharingCommentLikeController {
    private final SharingCommentLikeService sharingCommentLikeService;

    // [가계부 공유] 댓글 좋아요
    @PostMapping("/{commentId}")
    public String postCommentLike(@PathVariable("commentId") Long commentId) {
        sharingCommentLikeService.newLike(commentId);
        return "";
    }
}
