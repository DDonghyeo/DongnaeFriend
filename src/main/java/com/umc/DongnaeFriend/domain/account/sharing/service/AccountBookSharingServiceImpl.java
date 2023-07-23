package com.umc.DongnaeFriend.domain.account.sharing.service;

import com.umc.DongnaeFriend.domain.account.sharing.dto.SharingDto;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingImg;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingBoardRepository;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingCommentRepository;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingImgRepository;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingSympathyRepository;
import com.umc.DongnaeFriend.domain.dongnae.entity.Dongnae;
import com.umc.DongnaeFriend.domain.type.Age;
import com.umc.DongnaeFriend.domain.type.Gender;
import com.umc.DongnaeFriend.domain.type.SharingCategory;
import com.umc.DongnaeFriend.domain.type.YesNo;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.umc.DongnaeFriend.global.util.TimeUtil.getTime;

@Slf4j
@Service
public class AccountBookSharingServiceImpl implements AccountBookSharingService {


    //임시 유저
    Dongnae dongnae = Dongnae.builder().id(1L).gu("서울구").dong("서울동").city("서울시").townName("무슨마을").build();
    User user = User.builder().id(1L).age(Age.AGE10).email("email").dongnae(dongnae).gender(Gender.FEMALE).infoCert(YesNo.NO).townCert(YesNo.NO).townCertCnt(10).id(1L).kakaoId(90L).nickname("nickname").refreshToken("refreshToken").build();

    @Autowired
    private SharingBoardRepository sharingBoardRepository;

    @Autowired
    private SharingImgRepository sharingImgRepository;

    @Autowired
    private SharingCommentRepository sharingCommentRepository;

    @Autowired
    private SharingSympathyRepository sharingSympathyRepository;


    /*
     * [가계부 공유] 게시글 검색
     */
    @Override
    public List<SharingDto.ListResponse> searchByKeyword(String keyword, int category, Pageable pageable) {
        //TODO : 전체 카테고리 처리
        List<SharingBoard> sharingBoards = sharingBoardRepository.findByKeywordOrderByLikes(keyword, SharingCategory.valueOf(category).name(), pageable);
        if (sharingBoards.isEmpty()) {
            throw new CustomException(ErrorCode.NO_CONTENT_FOUND);
        }
        log.info("board found" + sharingBoards.get(0).getId());
        return getListResponses(sharingBoards);
    }

    /*
     * [가계부 공유] 게시글 등록
     */
    @Override
    public void createPost(SharingDto.Request req) {
        sharingBoardRepository.save(SharingBoard.builder()
                .category(SharingCategory.valueOf(req.getCategory()))
                .title(req.getTitle())
                .content(req.getContent())
                .build()
        );
        //TODO : Img 파일 업로드
    }






    //ListResponse 변환
    private List<SharingDto.ListResponse> getListResponses(List<SharingBoard> sharingBoardList) {
        return sharingBoardList.stream()
                .map(origin -> SharingDto.ListResponse.builder()
                        .id(origin.getId())
                        .category(origin.getCategory().getValue())
                        .title(origin.getTitle())
                        .content(origin.getContent())
                        .imageUrl(
                                sharingImgRepository.findFirst(origin.getId()).map(SharingImg::getImageUrl).orElse("")
                        )
                        .createdAt(
                                getTime(origin.getCreatedAt())
                        )
                        .view(origin.getView())
                        .commentCount(
                                sharingCommentRepository.countAllBySharingBoardId(origin.getId())
                        )
                        .likes(
                                sharingSympathyRepository.countAllBySharingBoardId(origin.getId())
                        )
                        .build())
                .collect(Collectors.toList());
    }



}
