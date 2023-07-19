package com.umc.DongnaeFriend.domain.dongnae.controller;

import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeBoardRepository;
import com.umc.DongnaeFriend.domain.dongnae.service.DongnaeBoardService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
     * [가계부 공유] 게시글 검색
     * @param keyword
     * @param category
     * @param sort
     */
    @GetMapping("/search")
    public ResponseEntity<?> getBoards(@RequestParam("keyword") String keyword,
                                       @RequestParam("category") int category,
                                       @RequestParam("sortBy") int sort) {
        return ResponseEntity.ok(dongnaeBoardService.searchByKeyword(keyword, category, sort));
    }


    /*
     * [가계부 공유] 게시글 목록 조회
     * @param sort
     */
    @GetMapping("")
    public ResponseEntity<?> postBoard(@RequestParam("sortBy") int sort) {
        return ResponseEntity.ok(dongnaeBoardService.searchAll(sort));
    }


}
