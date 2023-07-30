package com.umc.DongnaeFriend.domain.profile.service;

import com.umc.DongnaeFriend.domain.account.sharing.dto.SharingDto;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingImg;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingBoardRepository;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingCommentRepository;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingImgRepository;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingSympathyRepository;
import com.umc.DongnaeFriend.domain.profile.dto.AccountBookProfileDto;
import com.umc.DongnaeFriend.domain.profile.dto.UserProfileDto;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.umc.DongnaeFriend.global.util.TimeUtil.getTime;

/**
 * TODO : 공감, 스크랩 게시물 조회 필요
 */
@Service
@RequiredArgsConstructor
public class AccountBookProfileService {

    private final UserRepository userRepository;
    private final SharingBoardRepository sharingBoardRepository;
    private final SharingCommentRepository sharingCommentRepository;
    private final SharingSympathyRepository sharingSympathyRepository;
    private final SharingImgRepository sharingImgRepository;

    // 본인 or 타사용자 확인
    private User checkUser(Long userId){
        User user;
        if(userId==null){ // 유저아이디가 없으면 본인
            user = findUser();
        }else{
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        }
        return user;
    }

    /**
     * 가계부 공유 프로필 조회
     */
    public AccountBookProfileDto.AccountBookProfileResponse getAbSharing(Long userId, int category, Pageable pageable){
        User user = checkUser(userId);

        // 유저 아이디가 있으면 타사용자, 유저아이디가 없으면 본인
        return AccountBookProfileDto.AccountBookProfileResponse.builder()
                .userId(userId==null ? findUser().getId() : userId)
                .isMine(user.getId().equals(findUser().getId()))
                .postTotalCount(sharingBoardRepository.countAllByUserId(user.getId()))
                .commentTotalCount(sharingCommentRepository.countAllByUserId(user.getId()))
                .likedTotalCount(sharingSympathyRepository.countAllByUserId(user.getId()))
                .profile(UserProfileDto.UserProfileResponseDto.of(user))
                .content(getWrittenContent(user.getId(), category, pageable))
                .build();
    }

    /**
     * 가계부 공유 - 작성한 글 , 작성한 댓글의 게시글 조회
     * TODO : 공감, 스크랩 글 조회 필요
     */
    public List<SharingDto.AccountBookProfileListResponse> getWrittenContent(Long userId, int category, Pageable pageable) {
        User user = checkUser(userId);

        List<SharingBoard> sharingBoardList;
        if(category==0){
            sharingBoardList= sharingBoardRepository.findAllByUserId(user.getId(), pageable);
        }else{
            sharingBoardList = sharingCommentRepository.getCommentByUserIdAndBoard(user.getId())
                    .stream().map(SharingComment::getSharingBoard).distinct().collect(Collectors.toList());
        }
        return getProfileListResponse(sharingBoardList);
    }


    //ListResponse 변환
    private List<SharingDto.AccountBookProfileListResponse> getProfileListResponse(List<SharingBoard> sharingBoardList){
        return sharingBoardList.stream()
                .map(sharingBoard -> SharingDto.AccountBookProfileListResponse.builder()
                        .boardId(sharingBoard.getId())
                        //.town(sharingBoard.getPlaceLocation())
                        .category(sharingBoard.getCategory().getValue())
                        .title(sharingBoard.getTitle())
                        .imageUrl(sharingImgRepository.findFirst(sharingBoard.getId()).map(SharingImg::getImageUrl).orElse(""))
                        .createdAt(getTime(sharingBoard.getCreatedAt()))
                        .commentCount(sharingCommentRepository.countAllBySharingBoardId(sharingBoard.getId()))
                        .likeCount(sharingSympathyRepository.countAllBySharingBoardId(sharingBoard.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    public User findUser() {
        Object userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById((Long) userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
