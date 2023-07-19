package com.umc.DongnaeFriend.domain.dongnae.service;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeBoardDto;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeImg;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeBoardRepository;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeCommentRepository;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeImgRepository;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeSympathyRepository;
import com.umc.DongnaeFriend.domain.type.DongnaeBoardCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
     * [가계부 공유] 게시글 목록 조회
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



