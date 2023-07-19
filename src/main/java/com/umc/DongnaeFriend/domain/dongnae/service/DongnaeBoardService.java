package com.umc.DongnaeFriend.domain.dongnae.service;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeBoardDto;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.type.DongnaeBoardCategory;

import java.util.List;

public interface DongnaeBoardService {

    public List<DongnaeBoardDto.ListResponse> getBoard(String keyword, int category);
}
