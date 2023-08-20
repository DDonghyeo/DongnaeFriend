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
import org.springframework.transaction.annotation.Propagation;
import com.umc.DongnaeFriend.domain.type.SharingCategory;
import com.umc.DongnaeFriend.domain.type.YesNo;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import com.umc.DongnaeFriend.global.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import static com.umc.DongnaeFriend.global.util.TimeUtil.getTime;

@Slf4j
@Service
public class AccountBookSharingServiceImpl implements AccountBookSharingService {

    @Autowired
    private SharingBoardRepository sharingBoardRepository;

    @Autowired
    private SharingImgRepository sharingImgRepository;

    @Autowired
    private SharingCommentRepository sharingCommentRepository;

    @Autowired
    private SharingSympathyRepository sharingSympathyRepository;

    @Autowired
    private UserRepository userRepository;


    /*
     * [가계부 공유] 게시글 검색
     */
    @Override
    public List<SharingDto.ListResponse> searchByKeyword(String keyword, int category, Pageable pageable) {
        //TODO : 전체 카테고리 처리
        List<SharingBoard> sharingBoards = sharingBoardRepository.findByKeyword(keyword, SharingCategory.valueOf(category).name(), pageable);
        if (sharingBoards.isEmpty()) {
            throw new CustomException(ErrorCode.NO_CONTENT_FOUND);
        }
        return getListResponses(sharingBoards);
    }

    /*
     * [가계부 공유] 게시글 등록
     */
    @Override
    public void createPost(SharingDto.Request req) {
        User user = getCurUser();
        sharingBoardRepository.save(req.toEntity(user));
        //TODO : Img 파일 업로드
    }


    /*
     * [가계부 공유] 게시글 상세 조회
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SharingDto.Response getBoard(long board_id) {
        SharingBoard board = sharingBoardRepository.findById(board_id).orElseThrow(() ->
                new CustomException(ErrorCode.NO_CONTENT_FOUND));

        User user = getCurUser();

        board.updateView();

        //Get Images
        List<SharingImg> images = sharingImgRepository.findAllBySharingBoard_Id(board_id);

        //Writer인지 검사
        boolean isWriter = Objects.equals(board.getUser().getId(), user.getId());

        //LikeOrNot 검사
        boolean likeOrNot = !sharingSympathyRepository.findByUser_Id(user.getId()).isEmpty();

        //TODO: ScrapRepository 필요
        //scrapOrNot 검사
        boolean scrapOrNot = false;

        return SharingDto.Response.builder()
                .profileImage(board.getUser().getProfileImage())
                .nickname(board.getUser().getNickname())
                .category(board.getCategory().getValue())
                .title(board.getTitle())
                .content(board.getContent())
                .images(images.stream().map(SharingImg::getImageUrl).collect(Collectors.toList()))
                .createdAt(getTime(board.getCreatedAt()))
                .isWriter(isWriter)
                .likeOrNot(likeOrNot)
                .scrapOrNot(scrapOrNot)
                .view(board.getView()).build();
    }

    /*
     * [가계부 공유] 게시글 수정
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateBoard(long board_id, SharingDto.Request req) {

        SharingBoard board = sharingBoardRepository.findById(board_id).orElseThrow(
                () -> new CustomException(ErrorCode.NO_CONTENT_FOUND));

        //권한 검사
        if (!board.getUser().getId().equals(getCurUser().getId())) throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);

        board.updateBoard(req);
        sharingBoardRepository.save(board);

        sharingImgRepository.findAllBySharingBoard_Id(board_id);

        //TODO: File Upload
    }

    /*
     * [가계부 공유] 게시글 삭제
     */
    @Override
    public void deleteBoard(long board_id) {
        User user = getCurUser();
        Optional<SharingBoard> sharingBoard = sharingBoardRepository.findById(board_id);
        if (sharingBoard.isEmpty()) {
            throw new CustomException(ErrorCode.NO_CONTENT_FOUND);
        }

        if (!Objects.equals(sharingBoard.get().getUser().getId(), user.getId())) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }


        //게시글 삭제
        sharingBoardRepository.deleteById(board_id);
        //이미지 삭제
        sharingImgRepository.findAllBySharingBoard_Id(board_id);
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


    private User getCurUser() {
        Long user_id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(user_id).orElseThrow(() ->
                new CustomException(ErrorCode.UNAUTHORIZED_MEMBER)
        );
    }
}
