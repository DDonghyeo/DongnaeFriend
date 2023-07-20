package com.umc.DongnaeFriend.domain.dongnae.service;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeBoardDto;
import com.umc.DongnaeFriend.domain.dongnae.dto.UserLocationDto;
import com.umc.DongnaeFriend.domain.dongnae.entity.Dongnae;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeImg;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeBoardRepository;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeCommentRepository;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeImgRepository;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeSympathyRepository;
import com.umc.DongnaeFriend.domain.type.Age;
import com.umc.DongnaeFriend.domain.type.DongnaeBoardCategory;
import com.umc.DongnaeFriend.domain.type.Gender;
import com.umc.DongnaeFriend.domain.type.YesNo;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

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

    /*
     * [동네정보] 홈 화면
     * 카테고리 별 게시글 2개씩 반환
     * @param sort
     */
    public List<DongnaeBoardDto.ListResponse> home(int category) {
        String category_String = DongnaeBoardCategory.valueOf(category).name();
        //TODO : 동네 인증 여부 확인하기 - (User 필요)
        String category_ = "RESTAURANT";
        List<DongnaeBoard> dongnaeBoardList = dongnaeBoardRepository.findTwoByCategoryOrderByCreatedAt(category_);
        return getListResponses(dongnaeBoardList);
    }

    /*
     * [동네정보] 사용자 위치 정보
     */
    public UserLocationDto getUserLocaiton() {
        //TODO : 사용자 식별자 가져오기 - (User 필요)
        long user_id = 1;
        return new UserLocationDto("서울도시");
    }


    /*
     * [동네정보] 게시글 검색
     * @param sort
     */

//    @Transactional(propagation = Propagation.REQUIRED)
    public List<DongnaeBoardDto.ListResponse> searchByKeyword(String keyword, int category, int sort) {
        String categoryName = DongnaeBoardCategory.valueOf(category).name();


        List<DongnaeBoard> dongnaeBoardList;
        if (sort == 0) {
            dongnaeBoardList = dongnaeBoardRepository.findByKeywordOrderByCreatedAt(keyword, categoryName);
        } else {
            dongnaeBoardList = dongnaeBoardRepository.findByKeywordOrderByLikes(keyword, categoryName);
        }

        return getListResponses(dongnaeBoardList);
    }

    /*
     * [동네정보] 게시글 목록 조회
     * @param sort
     */
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
     * [동네정보] 게시글 목록 조회
     */
    public void createBoard(DongnaeBoardDto.Request req) {
        //TODO : User Mapping UserRepository 필요.
        Dongnae dongnae = Dongnae.builder().id(1L).gu("서울구").dong("서울동").city("서울시").townName("무슨마을").build();
        User user = User.builder().age(Age.AGE10).email("email").dongnae(dongnae).gender(Gender.FEMALE).infoCert(YesNo.NO).townCert(YesNo.NO).id(1L).kakaoId(90L).nickname("nickname").refreshToken("refreshToken").build();

        dongnaeBoardRepository.save(req.toEntity(user, dongnae));
    }


    //ListResponse 변환
    private List<DongnaeBoardDto.ListResponse> getListResponses(List<DongnaeBoard> dongnaeBoardList) {
        return dongnaeBoardList.stream()
                .map(origin -> DongnaeBoardDto.ListResponse.builder()
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


    //시간 계산
    private String getTime(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now(); // 현재 시간
        Duration duration = Duration.between(now, time);

        log.info(now.toString());
        log.info(time.toString());
        long days = duration.toDays();
        log.info(" days: "+ days);
        long hours = duration.toHours() % 24;
        log.info(" hours: "+ hours);
        long minutes = duration.toMinutes() % 60;
        log.info(" minutes: "+ minutes);

        if (days > 1) return days + "일 전";
        else if (hours >= 1) {
            return hours + "시간 전";
        } else return minutes + "분 전";

    }

}



