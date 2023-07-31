package com.umc.DongnaeFriend.domain.scrap.controller;

import com.umc.DongnaeFriend.domain.scrap.dto.ReqScrapDto;
import com.umc.DongnaeFriend.domain.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/scrap")
public class ScrapController {
    private final ScrapService scrapService;

    // 스크랩
    @PostMapping("")
    public String postScrap(@RequestBody ReqScrapDto reqScrapDto) {
        scrapService.newScrap(reqScrapDto);
        return "";
    }
}
