package com.umc.DongnaeFriend.domain.profile.service;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeBoardDto;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeComment;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeImg;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeBoardRepository;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeCommentRepository;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeImgRepository;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeSympathyRepository;
import com.umc.DongnaeFriend.domain.profile.dto.DongnaeProfileDto;
import com.umc.DongnaeFriend.domain.profile.dto.UserProfileDto;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.umc.DongnaeFriend.global.util.TimeUtil.getTime;

@Service
@RequiredArgsConstructor
public class DongnaeProfileService {

    private final DongnaeSympathyRepository dongnaeSympathyRepository;
    private final DongnaeCommentRepository commentRepository;
    private final DongnaeBoardRepository dongnaeBoardRepository;
    private final DongnaeImgRepository dongnaeImgRepository;
    private final UserRepository userRepository;


    // 본인 or 타사용자 확인
    private User checkUser(Long userId){
        User user;
        if(userId==null){ // 유저아이디가 없으면 본인
            user = userRepository.findById(userId/*본인인증 필요*/)
                    .orElseThrow();
        }else{
            user = userRepository.findById(userId)
                    .orElseThrow();
        }
        return user;
    }

    /**
     * 동네 정보 프로필 조회
     */
    public DongnaeProfileDto.DongnaeProfileResponse getDongnaeProfile(Long userId, int category){
        User user = checkUser(userId);

        // 유저 아이디가 있으면 타사용자, 유저아이디가 없으면 본인

        return DongnaeProfileDto.DongnaeProfileResponse.builder()
                .userId(userId==null ? user.getId() /*본인인증 필요*/ : userId)
                .nickname(user.getNickname())
                .isMine(userId.equals(user.getId() /*본인인증 필오*/))
                .profileImage(user.getProfileImage())
                .postTotalCount(dongnaeBoardRepository.countAllByUserId(user.getId()))
                .commentTotalCount(commentRepository.countAllByUserId(user.getId()))
                .likedTotalCount(dongnaeSympathyRepository.countAllByUserId(user.getId()))
                .profile(UserProfileDto.UserProfileResponseDto.of(user))
                .content(getWrittenContent(user.getId(), category))
                .build();
    }
🏝️
    /**
     * 동네정보 - 작성한 글 , 작성한 댓글의 게시글 조회
     * TODO : 공감, 스크랩 게시물 조회 필요
     */
    public List<DongnaeBoardDto.DongnaeProfileListResponse> getWrittenContent(Long userId, int category) {
        User user = checkUser(userId);

        List<DongnaeBoard> dongnaeBoardList;
        if(category==0){
            dongnaeBoardList= dongnaeBoardRepository.findAllByUserId(user.getId());
        }else{
            dongnaeBoardList = commentRepository.getCommentByUserIdAndBoard(user.getId())
                    .stream().map(DongnaeComment::getDongnaeBoard).distinct().collect(Collectors.toList());
        }
        return getProfileListResponse(dongnaeBoardList);
    }
🏝️
    //ListResponse 변환
    private List<DongnaeBoardDto.DongnaeProfileListResponse> getProfileListResponse(List<DongnaeBoard> dongnaeBoardList){
        return dongnaeBoardList.stream()
                .map(dongnaeBoard -> DongnaeBoardDto.DongnaeProfileListResponse.builder()
                        .id(dongnaeBoard.getId())
                        .town(dongnaeBoard.getPlaceLocation())
                        .category(dongnaeBoard.getCategory().getValue())
                        .title(dongnaeBoard.getTitle())
                        .imageUrl(dongnaeImgRepository.findFirst(dongnaeBoard.getId()).map(DongnaeImg::getImageUrl).orElse(""))
                        .createdAt(getTime(dongnaeBoard.getCreatedAt()))
                        .commentCount(commentRepository.countAllByDongnaeBoardId(dongnaeBoard.getId()))
                        .likeCount(dongnaeSympathyRepository.countAllByDongnaeBoardId(dongnaeBoard.getId()))
                        .build())
                .collect(Collectors.toList());
    }
}
