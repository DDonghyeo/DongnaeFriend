package com.umc.DongnaeFriend.domain.account.sharing.service;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingSympathy;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingSympathyRepository;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SharingSympathyService {
    private final SharingSympathyRepository sharingSympathyRepository;
    private final UserRepository userRepository;


    public String newSympathy(Long accountBookId){
        User user = findUser();

        // 게시판 가져오기
        SharingBoard sharingBoard = sharingSympathyRepository.findBySharingBoardId(accountBookId);

        // 공감 유무 확인하기
        SharingSympathy sharingSympathyExist = sharingSympathyRepository.findBySharingBoardId(sharingBoard);

        if (sharingSympathyExist == null) {
            // 공감 데이터 저장하기
            SharingSympathy sharingSympathy = SharingSympathy.builder()
                    .sharingBoard(sharingBoard)
                    .user(user)
                    .build();

            sharingSympathyRepository.save(sharingSympathy);

            return "[가계부 공유] 공감 성공";
        }

        // 공감 데이터 삭제하기
        sharingSympathyRepository.delete(sharingSympathyExist);

        return "[가계부 공유] 공감 삭제 성공";
    }

    public User findUser() {
        Object userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById((Long) userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
