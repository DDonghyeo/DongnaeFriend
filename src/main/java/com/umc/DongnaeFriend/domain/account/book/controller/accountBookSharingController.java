package com.umc.DongnaeFriend.domain.account.book.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account-books/sharing")
public class accountBookSharingController {

    /**
     * [가계부 공유] 게시글 검색
     */

    @GetMapping("/search")
    public ResponseEntity<?> searchAll(@RequestParam("keyword")String keyword,  Pageable pageable) {

    }

}
