package com.umc.DongnaeFriend.domain.account.book.controller;

import com.umc.DongnaeFriend.domain.account.book.dto.MemoDto;
import com.umc.DongnaeFriend.domain.account.book.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/memo")
public class MemoController {

    private final MemoService memoService;

    /**
     * 유저 권한 확인 필요
     */
    @GetMapping
    public ResponseEntity<MemoDto.MemoListResponse> getMemoList(@RequestParam(value = "year", required = false) Integer year,
                                                               @RequestParam(value = "month", required = false) Integer month){
        return ResponseEntity.status(HttpStatus.OK).body(memoService.getMemoList(year, month));
    }

    @PostMapping
    public ResponseEntity<?> createMemo(@RequestParam(value = "year", required = false) Integer year,
                           @RequestParam(value = "month", required = false) Integer month,
                           @RequestBody MemoDto.MemoRequest requestDto){
        memoService.createMemo(requestDto, year,month);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateMemo(@RequestParam(value = "id", required = false) Long id,
                           @RequestBody MemoDto.MemoRequest requestDto){
        memoService.updateMemo(requestDto, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMemo(@RequestParam(value = "id", required = false) Long id){
        memoService.deleteMemo(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
