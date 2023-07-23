package com.umc.DongnaeFriend.domain.profile.controller;


import com.umc.DongnaeFriend.domain.profile.dto.MyPageDto;
import com.umc.DongnaeFriend.domain.profile.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/api/user")
    public MyPageDto.MyPageResponseDto getMyPage(){
        return myPageService.getMyPage();
    }

    @PutMapping("/api/user")
    public void updateMyPage(@RequestBody MyPageDto.MyPageRequestDto myPageRequest){
        myPageService.updateMyPage(myPageRequest);
    }
}
