package com.umc.DongnaeFriend.domain.profile.controller;

import com.umc.DongnaeFriend.domain.profile.dto.AccountBookProfileDto;
import com.umc.DongnaeFriend.domain.profile.service.AccountBookProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountBookProfileController {

    private final AccountBookProfileService accountBookProfileService;

    // 동네정보 프로필 조회
    @GetMapping({"/api/my/account-books", "/api/{userId}/account-books"})
    public ResponseEntity<AccountBookProfileDto.AccountBookProfileResponse> getProfile(@PathVariable(value = "userId", required = false) Long userId,
                                                                                      @RequestParam int category, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(accountBookProfileService.getAbSharing(userId, category, pageable));
    }
}
