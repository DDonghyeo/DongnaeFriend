package com.umc.DongnaeFriend.domain.dongnae.service;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeBoardDto;
import com.umc.DongnaeFriend.domain.dongnae.dto.UserLocationDto;
import com.umc.DongnaeFriend.domain.dongnae.entity.Dongnae;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeImg;
import com.umc.DongnaeFriend.domain.dongnae.respository.*;
import com.umc.DongnaeFriend.domain.type.Age;
import com.umc.DongnaeFriend.domain.type.DongnaeBoardCategory;
import com.umc.DongnaeFriend.domain.type.Gender;
import com.umc.DongnaeFriend.domain.type.YesNo;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.umc.DongnaeFriend.global.util.TimeUtil.getTime;

@Slf4j
@Service
public class DongnaeBoardServiceImpl implements DongnaeBoardService {

    //임시 유저
    Dongnae dongnae = Dongnae.builder().id(1L).gu("서울구").dong("서울동").city("서울시").townName("무슨마을").build();
    User user = User.builder().id(1L).age(Age.AGE10).email("email").dongnae(dongnae).gender(Gender.FEMALE).infoCert(YesNo.NO).townCert(YesNo.NO).townCertCnt(10).id(1L).kakaoId(90L).nickname("nickname").refreshToken("refreshToken").build();

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
        //TODO : 사용자 식별자 가져오기 - (User 필요)
        long user_id = 1;
        return new UserLocationDto("서울도시");
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
     * [동네정보] 게시글 목록 조회 DLETED
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
        //TODO : User Mapping UserRepository 필요.
        dongnaeBoardRepository.save(req.toEntity(user, dongnae));
    }



    /*
     * [동네정보] 게시글 상세 조회
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DongnaeBoardDto.Response getBoard(long board_id) {
        //TODO : User 식별자 필요.
        Optional<DongnaeBoard> board = dongnaeBoardRepository.findById(board_id);
        if (board.isEmpty()) {
            throw new CustomException(ErrorCode.NO_CONTENT_FOUND);
        }

        //Get Images
        List<DongnaeImg> images = dongnaeImgRepository.findAllByDongnaeBoard_Id(board_id);

        //Writer인지 검사
        boolean isWriter = Objects.equals(board.get().getUser().getId(), user.getId());

        //LikeOrNot 검사
        boolean likeOrNot = !dongnaeSympathyRepository.findByUser_Id(user.getId()).isEmpty();

        //TODO: ScrapRepository 필요
        //scrapOrNot 검사
        boolean scrapOrNot = false;

        return DongnaeBoardDto.Response.builder()

                .profileImage(user.getProfileImage())
                .nickname(user.getNickname())
                .category(board.get().getCategory().getValue())
                .title(board.get().getTitle())
                .content(board.get().getContent())
                .images(images.stream().map(DongnaeImg::getImageUrl).collect(Collectors.toList()))
                .place(board.get().getPlace())
                .placeLocation(board.get().getPlaceLocation())
                .createdAt(getTime(board.get().getCreatedAt()))
                .townCertification(user.getTownCertCnt())
                .isWriter(isWriter)
                .likeOrNot(likeOrNot)
                .scrapOrNot(scrapOrNot)
                .view(board.get().getView()).build();
    }


    /*
     * [동네정보] 게시글 수정
     */
    @Override
    public void updateBoard(long board_id, DongnaeBoardDto.Request request) {
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
    public void deleteBoard(long board_id) throws AuthenticationException {
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
}



