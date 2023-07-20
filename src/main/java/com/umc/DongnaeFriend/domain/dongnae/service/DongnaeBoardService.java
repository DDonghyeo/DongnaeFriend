package com.umc.DongnaeFriend.domain.dongnae.service;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeBoardDto;
import com.umc.DongnaeFriend.domain.dongnae.dto.UserLocationDto;

import javax.naming.AuthenticationException;
import java.util.List;

public interface DongnaeBoardService {

    List<DongnaeBoardDto.ListResponse> searchByKeyword(String keyword, int category, int sort);

    List<DongnaeBoardDto.ListResponse> searchAll(int sort);

    void createBoard(DongnaeBoardDto.Request req);

    List<DongnaeBoardDto.ListResponse> home(int category);

    UserLocationDto getUserLocation();

    DongnaeBoardDto.Response getBoard(long board_id);

    void updateBoard(long board_id, DongnaeBoardDto.Request request) throws AuthenticationException;

    void deleteBoard(long board_id) throws AuthenticationException;
}
