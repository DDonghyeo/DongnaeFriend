package com.umc.DongnaeFriend.domain.scrap.service;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.scrap.dto.ReqScrapDto;
import com.umc.DongnaeFriend.domain.scrap.entity.Scrap;
import com.umc.DongnaeFriend.domain.scrap.repository.ScrapRepository;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScrapService {
    private final ScrapRepository scrapRepository;

    public String newScrap(ReqScrapDto reqScrapDto) {
        // !임시! 유저 가져오기
        User user = scrapRepository.findByUserId(1L);

        // 가계부 공유
        if (!(reqScrapDto.getAccountBookId() == null)) {
            SharingBoard sharingBoard = scrapRepository.findBySharingBoardId(reqScrapDto.getAccountBookId());

            // 스크랩 정보 저장
            Scrap scrap = Scrap.builder()
                    .sharingBoard(sharingBoard)
                    .user(user)
                    .build();
        }

        // 동네정보
        else if (!(reqScrapDto.getTownInformationId() == null)) {
            DongnaeBoard dongnaeBoard = scrapRepository.findByDongnaeBoardId(reqScrapDto.getTownInformationId());

            // 스크랩 정보 저장
            Scrap scrap = Scrap.builder()
                    .dongnaeBoard(dongnaeBoard)
                    .user(user)
                    .build();
        }

        else {
            return "스크랩 실패";
        }

        return "스크랩 성공";
    }
}
