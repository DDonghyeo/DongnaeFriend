package com.umc.DongnaeFriend.domain.profile.controller;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeBoardDto;
import com.umc.DongnaeFriend.domain.profile.dto.DongnaeProfileDto;
import com.umc.DongnaeFriend.domain.profile.dto.MyPageDto;
import com.umc.DongnaeFriend.domain.profile.service.DongnaeProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DongnaeProfileController {

    private final DongnaeProfileService dongnaeProfileService;

    // 동네정보 프로필 조회
    @GetMapping({"/api/my/town", "/api/{userId}/town"})
    public DongnaeProfileDto.DongnaeProfileResponse getProfile(@PathVariable(value = "userId", required = false) Long userId,
                                                               @RequestParam int category, Pageable pageable){
        return dongnaeProfileService.getDongnaeProfile(userId, category, pageable);
    }

}
