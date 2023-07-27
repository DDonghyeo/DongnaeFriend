package com.umc.DongnaeFriend.domain.profile.controller;


import com.umc.DongnaeFriend.domain.profile.dto.MyPageDto;
import com.umc.DongnaeFriend.domain.profile.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/api/user")
    public ResponseEntity<MyPageDto.MyPageResponseDto> getMyPage(){
        return ResponseEntity.status(HttpStatus.OK).body(myPageService.getMyPage());
    }

    @PutMapping("/api/user")
    public ResponseEntity<?> updateMyPage(@RequestPart(value = "request", required = false) MyPageDto.MyPageRequestDto myPageRequest,
                             @RequestPart(value = "image", required = false) MultipartFile image){

        log.info("updateMyPage - 프로필 사진 변경");
        myPageService.updateMyPage(myPageRequest, image);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
