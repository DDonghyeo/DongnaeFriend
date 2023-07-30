package com.umc.DongnaeFriend.domain.account.sharing.controller;


import com.umc.DongnaeFriend.domain.account.sharing.service.SharingSympathyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account-books/sharing/likes")
public class SharingSympathyController {
    private final SharingSympathyService sharingSympathyService;

    // 게시글 공감하기
    @PostMapping("/{accountBookId}")
    public String postSympathy(@PathVariable("accountBookId") Long accountBookId) {
        sharingSympathyService.newSympathy(accountBookId);
        return "";
    }
}
