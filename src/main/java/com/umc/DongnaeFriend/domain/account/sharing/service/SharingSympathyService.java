package com.umc.DongnaeFriend.domain.account.sharing.service;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingSympathy;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingSympathyRepository;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SharingSympathyService {
    private final SharingSympathyRepository sharingSympathyRepository;

    public String newSympathy(Long accountBookId){
        // !임시! 유저 가져오기
        User user = sharingSympathyRepository.findByUserId(1L);

        // 게시판 가져오기
        SharingBoard sharingBoard = sharingSympathyRepository.findBySharingBoardId(accountBookId);

        // 공감 데이터 저장하기
        SharingSympathy sharingSympathy = SharingSympathy.builder()
                .sharingBoard(sharingBoard)
                .user(user)
                .build();

        sharingSympathyRepository.save(sharingSympathy);

        return "[가계부 공유] 공감 성공";
    }

}
