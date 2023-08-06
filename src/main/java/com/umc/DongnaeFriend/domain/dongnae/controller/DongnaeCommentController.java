package com.umc.DongnaeFriend.domain.dongnae.controller;

import com.umc.DongnaeFriend.domain.account.sharing.dto.ReqSharingCommentDto;
import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeCommentDto;
import com.umc.DongnaeFriend.domain.dongnae.service.DongnaeCommentService;
import lombok.RequiredArgsConstructor;
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


}
