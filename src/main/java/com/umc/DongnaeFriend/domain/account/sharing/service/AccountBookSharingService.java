package com.umc.DongnaeFriend.domain.account.sharing.service;

import com.umc.DongnaeFriend.domain.account.sharing.dto.SharingDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountBookSharingService {

    List<SharingDto.ListResponse> searchByKeyword(String keyword, int category, Pageable pageable);

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
