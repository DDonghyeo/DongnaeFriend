package com.umc.DongnaeFriend.domain.account.sharing.controller;

import com.umc.DongnaeFriend.domain.account.sharing.dto.SharingDto;
import com.umc.DongnaeFriend.domain.account.sharing.service.AccountBookSharingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/account-books/sharing")
public class accountBookSharingController {

    @Autowired
    AccountBookSharingService accountBookSharingService;

    /*
     * [가계부 공유] 게시글 검색
     * @param keyword
     * @param pageable
     *
     * @param pageable
     * size : 페이지 사이즈
     * page : 페이지
     * sortBy : 정렬순
     *  - like
     *  - createdAt
     */

    @GetMapping("/search")

    public ResponseEntity<?> searchAll(@RequestParam("keyword") String keyword, @RequestParam("category") int category, Pageable pageable) {
        log.info("searching : " + keyword + category);
        List<SharingDto.ListResponse> res = accountBookSharingService.searchByKeyword(keyword, category, pageable);
        log.info("res ");
        return ResponseEntity.ok(res);
    }

    /*
     * [가계부 공유] 게시글 등록

     *
     * @param RequestDto
     */

    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestBody SharingDto.Request req) {
        accountBookSharingService.createPost(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * [가계부 공유] 게시글 상세 조회
     *
     * @PathVariable accountBookId
     */
    @GetMapping("/{accountBookId}")
    public ResponseEntity<?> updatePost(@PathVariable("accountBookId") long board_id) {
        return ResponseEntity.ok(accountBookSharingService.getBoard(board_id));
    }

    /*
     * [가계부 공유] 게시글 수정
     *
     * @PathVariable accountBookId
     * @RequestBody SharingDto.Request
     */
    @PutMapping("/{accountBookId}")
    public ResponseEntity<?> updateBoard(@PathVariable("accountBookId") int board_id, @RequestBody SharingDto.Request req) {
        accountBookSharingService.updateBoard(board_id, req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
