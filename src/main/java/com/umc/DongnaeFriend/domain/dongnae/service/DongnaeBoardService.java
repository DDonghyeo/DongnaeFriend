package com.umc.DongnaeFriend.domain.dongnae.service;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeBoardDto;

import java.util.List;

public interface DongnaeBoardService {

    List<DongnaeBoardDto.ListResponse> searchByKeyword(String keyword, int category, int sort);

    List<DongnaeBoardDto.ListResponse> searchAll(int sort);

    void createBoard(DongnaeBoardDto.Request req);

    List<DongnaeBoardDto.ListResponse> home(int category);
}
