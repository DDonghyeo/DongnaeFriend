package com.umc.DongnaeFriend.domain.account.sharing.service;

import com.umc.DongnaeFriend.domain.account.sharing.dto.SharingDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountBookSharingService {

    List<SharingDto.ListResponse> searchByKeyword(String keyword, int category, Pageable pageable);

    void createPost(SharingDto.Request req);
}
