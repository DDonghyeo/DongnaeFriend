package com.umc.DongnaeFriend.domain.dongnae.controller;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeBoardDto;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeBoardRepository;
import com.umc.DongnaeFriend.domain.dongnae.service.DongnaeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/*
 * [ 가계부 공유 ]
 * */

@RestController
@RequestMapping("/account-books/sharing")
public class DongnaeBoardController {

    @Autowired
    DongnaeBoardRepository dongnaeBoardRepository;

    @Autowired
    DongnaeBoardService dongnaeBoardService;

    /*
     * 게시글 검색 (전체)
     * */
    @GetMapping("/search")
    public ResponseEntity<?> getBoards(@RequestParam("keyword") String keyword,
                                       @RequestParam("category") int category,
                                       @RequestParam("sortBy") int sort) {
        return ResponseEntity.ok(dongnaeBoardService.getBoard(keyword, category, sort));
    }


    @PostMapping("")
    public ResponseEntity<?> postBoard(@RequestBody DongnaeBoardDto.Request req,
    @AuthenticationPrincipal Authentication auth) {
        dongnaeBoardRepository.save(req.toEntity());
        return null;
    }


}
