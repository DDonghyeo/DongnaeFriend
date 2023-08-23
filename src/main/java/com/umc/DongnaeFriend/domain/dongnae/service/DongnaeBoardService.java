package com.umc.DongnaeFriend.domain.dongnae.service;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeBoardDto;
import com.umc.DongnaeFriend.domain.dongnae.dto.UserLocationDto;
import com.umc.DongnaeFriend.global.dto.ContentsDto;
import org.springframework.data.domain.Pageable;

import javax.naming.AuthenticationException;
import java.util.List;

public interface DongnaeBoardService {

    ContentsDto searchByKeyword(String keyword, int category, Pageable pageable);

    List<DongnaeBoardDto.ListResponse> searchAll(int sort);

    void createBoard(DongnaeBoardDto.Request req);


    UserLocationDto getUserLocation();

    DongnaeBoardDto.Response getBoard(long board_id);

    void updateBoard(long board_id, DongnaeBoardDto.Request request) throws AuthenticationException;

    void deleteBoard(long board_id) throws AuthenticationException;

    String postLike(long board_id) throws AuthenticationException;
}
