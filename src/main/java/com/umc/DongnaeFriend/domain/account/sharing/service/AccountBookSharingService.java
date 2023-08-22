package com.umc.DongnaeFriend.domain.account.sharing.service;

import com.umc.DongnaeFriend.domain.account.sharing.dto.SharingDto;
import com.umc.DongnaeFriend.global.dto.ContentsDto;
import org.springframework.data.domain.Pageable;

public interface AccountBookSharingService {

    ContentsDto searchByKeyword(String keyword, int category, Pageable pageable);

    void createPost(SharingDto.Request req);

    /*
     * [가계부 공유] 게시글 상세 조회
     */
    SharingDto.Response getBoard(long board_id);

    /*
     * [가계부 공유] 게시글 수정
     */
    void updateBoard(long board_id, SharingDto.Request req);


    /*
     * [가계부 공유] 게시글 삭제
     */
    void deleteBoard(long board_id);
}
