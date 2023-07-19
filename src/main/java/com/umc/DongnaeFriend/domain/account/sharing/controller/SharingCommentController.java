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
    @PostMapping("/{sharing_board_id}")
    public String postComment(@PathVariable("sharing_board_id") Long sharing_board_id,  @RequestBody ReqSharingCommentDto reqSharingCommentDto) {
        sharingCommentService.newComment(sharing_board_id, reqSharingCommentDto);
        return "";
    }

}
