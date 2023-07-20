package com.umc.DongnaeFriend.domain.dongnae.controller;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeBoardDto;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeBoardRepository;
import com.umc.DongnaeFriend.domain.dongnae.service.DongnaeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * [ 가계부 공유 ]
 * */

@RestController
@RequestMapping("/town-information")
public class DongnaeBoardController {

    @Autowired
    DongnaeBoardRepository dongnaeBoardRepository;

    @Autowired
    DongnaeBoardService dongnaeBoardService;

    /*
     * [동네정보] 홈 화면
     */
    //TODO : 파라미터 다시 확인해보기
    @GetMapping("/home")
    public ResponseEntity<?> home(@RequestParam("category") int category,
                                       @RequestParam("sortBy") int sort) {
        return ResponseEntity.ok(dongnaeBoardService.home(category));
    }

    /*
     * [동네정보] 사용자 위치 정보
     */
    @GetMapping("/user/location")
    public ResponseEntity<?> getLocation() {
        return ResponseEntity.ok(dongnaeBoardService.getUserLocation());
    }


    /*
     * [동네정보] 게시글 검색
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
     * [동네정보] 게시글 목록조회
     * @param sort
     */
    @GetMapping("")
    public ResponseEntity<?> postBoard(@RequestParam("sortBy") int sort) {
        return ResponseEntity.ok(dongnaeBoardService.searchAll(sort));
    }


    /*
     * [동네정보] 게시글 등록
     * @param sort
     */
    @PostMapping("")
    public ResponseEntity<?> createBoard(@RequestBody DongnaeBoardDto.Request req) {
        dongnaeBoardService.createBoard(req);
        return ResponseEntity.ok("요청에 성공했습니다.");
    }

    /*
     * [동네정보] 게시글 상세 조회
     * @param sort
     */
    @GetMapping("/{townInformationId}")
    public ResponseEntity<?> getBoard(@PathVariable("townInformationId") int board_id) {
        return ResponseEntity.ok(dongnaeBoardService.getBoard(board_id));
    }






}
