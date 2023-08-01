package com.umc.DongnaeFriend.domain.dongnae.service;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeBoardDto;
import com.umc.DongnaeFriend.domain.dongnae.dto.UserLocationDto;
import com.umc.DongnaeFriend.domain.dongnae.entity.Dongnae;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeImg;
import com.umc.DongnaeFriend.domain.dongnae.respository.*;
import com.umc.DongnaeFriend.domain.type.DongnaeBoardCategory;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.umc.DongnaeFriend.global.util.TimeUtil.getTime;

@Slf4j
@Service
public class DongnaeBoardServiceImpl implements DongnaeBoardService {

    @Autowired
    private DongnaeBoardRepository dongnaeBoardRepository;

    @Autowired
    private DongnaeImgRepository dongnaeImgRepository;

    @Autowired
    private DongnaeCommentRepository dongnaeCommentRepository;

    @Autowired
    private DongnaeSympathyRepository dongnaeSympathyRepository;

    // 임시 유저, 동네 등록 //
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DongnaeRepository dongnaeRepository;


    /*
     * [동네정보] 사용자 위치 정보
     */
    @Override
    public UserLocationDto getUserLocation() {
        return new UserLocationDto(getCurUser().getDongnae().getTownName());
    }


    /*
     * [동네정보] 게시글 검색
     * @param sort
     */

    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
    public List<DongnaeBoardDto.ListResponse> searchByKeyword(String keyword, int category, Pageable pageable) {
        String categoryName = DongnaeBoardCategory.valueOf(category).name();

        List<DongnaeBoard> dongnaeBoardList = dongnaeBoardRepository.findByKeyword(keyword, categoryName, pageable);

        if (dongnaeBoardList.isEmpty()) {
            throw new CustomException(ErrorCode.NO_CONTENT_FOUND);
        }

        return getListResponses(dongnaeBoardList);
    }

    /*
     * [동네정보] 게시글 목록 조회 DELETED
     * @param sort
     */
    @Override
    public List<DongnaeBoardDto.ListResponse> searchAll(int sort) {

        List<DongnaeBoard> dongnaeBoardList;
        if (sort == 0) {
            dongnaeBoardList = dongnaeBoardRepository.findAllOrderByCreatedAt();
        } else {
            dongnaeBoardList = dongnaeBoardRepository.findAllOrderByLikes();
        }

        return getListResponses(dongnaeBoardList);
    }

    /*
     * [동네정보] 게시글 등록
     */
    @Override
    public void createBoard(DongnaeBoardDto.Request req) {
        User user = getCurUser();
        Dongnae dongnae = Dongnae.builder().id(1L).gu("서울구").dong("서울동").city("서울시").townName("무슨마을").build();

        dongnaeRepository.save(dongnae);
        // 임시 동네 저장
        dongnaeBoardRepository.save(req.toEntity(user,/*user.getDongnae()*/dongnae));
    }



    /*
     * [동네정보] 게시글 상세 조회
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DongnaeBoardDto.Response getBoard(long board_id) {
        User user = getCurUser();
        DongnaeBoard board = dongnaeBoardRepository.findById(board_id).orElseThrow(() ->
                new CustomException(ErrorCode.NO_CONTENT_FOUND));


        //Get Images
        List<DongnaeImg> images = dongnaeImgRepository.findAllByDongnaeBoard_Id(board_id);

        //Writer인지 검사
        boolean isWriter = Objects.equals(board.getUser().getId(), user.getId());

        //LikeOrNot 검사
        boolean likeOrNot = !dongnaeSympathyRepository.findByUser_Id(user.getId()).isEmpty();

        //TODO: ScrapRepository 필요
        //scrapOrNot 검사
        boolean scrapOrNot = false;

        return DongnaeBoardDto.Response.builder()

                .profileImage(board.getUser().getProfileImage())
                .nickname(board.getUser().getNickname())
                .category(board.getCategory().getValue())
                .title(board.getTitle())
                .content(board.getContent())
                .images(images.stream().map(DongnaeImg::getImageUrl).collect(Collectors.toList()))
                .place(board.getPlace())
                .placeLocation(board.getPlaceLocation())
                .createdAt(getTime(board.getCreatedAt()))
                .townCertification(user.getTownCertCnt())
                .isWriter(isWriter)
                .likeOrNot(likeOrNot)
                .scrapOrNot(scrapOrNot)
                .view(board.getView()).build();
    }


    /*
     * [동네정보] 게시글 수정
     */
    @Override
    public void updateBoard(long board_id, DongnaeBoardDto.Request request) {
        User user = getCurUser();
        Optional<DongnaeBoard> board = dongnaeBoardRepository.findById(board_id);
        if (board.isPresent()) {
            //User Validaiton
            if (!Objects.equals(board.get().getUser().getId(), user.getId())) {
                throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
            }
            board.get().updateBoard(request);
            dongnaeBoardRepository.save(board.get());
        } else {
            throw new CustomException(ErrorCode.INVALID_VALUE);
        }
    }

    @Override
    public void deleteBoard(long board_id) {
        User user = getCurUser();
        Optional<DongnaeBoard> board = dongnaeBoardRepository.findById(board_id);

        if (board.isEmpty())  throw new CustomException(ErrorCode.NO_CONTENT_FOUND);
        //User Validation
        if (!Objects.equals(board.get().getUser().getId(), user.getId())) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        } else {
            dongnaeBoardRepository.deleteById(board_id);
        }

    }


    //ListResponse 변환
    private List<DongnaeBoardDto.ListResponse> getListResponses(List<DongnaeBoard> dongnaeBoardList) {
        return dongnaeBoardList.stream()
                .map(origin -> DongnaeBoardDto.ListResponse.builder()
                        .id(origin.getId())
                        .town(origin.getPlace())
                        .category(origin.getCategory().getValue())
                        .title(origin.getTitle())
                        .content(origin.getContent())
                        .imageUrl(
                                dongnaeImgRepository.findFirst(origin.getId()).map(DongnaeImg::getImageUrl).orElse("")
                        )
                        .createdAt(
                                getTime(origin.getCreatedAt())
                        )
                        .view(origin.getView())
                        .commentCount(
                                dongnaeCommentRepository.countAllByDongnaeBoardId(origin.getId())
                        )
                        .likes(
                                dongnaeSympathyRepository.countAllByDongnaeBoardId(origin.getId())
                        )
                        .build())
                .collect(Collectors.toList());
    }


    private User getCurUser() {
        Long user_id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(user_id).orElseThrow(() ->
                new CustomException(ErrorCode.UNAUTHORIZED_MEMBER)
        );
    }
}



