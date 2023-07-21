package com.umc.DongnaeFriend.domain.account.book.service;

import com.umc.DongnaeFriend.domain.account.book.dto.SharingDto;
import com.umc.DongnaeFriend.domain.account.book.repository.SharingBoardRepository;
import com.umc.DongnaeFriend.domain.dongnae.entity.Dongnae;
import com.umc.DongnaeFriend.domain.type.Age;
import com.umc.DongnaeFriend.domain.type.Gender;
import com.umc.DongnaeFriend.domain.type.YesNo;
import com.umc.DongnaeFriend.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class accountBookSharingServiceImpl implements accountBookSharingService {


    //임시 유저
    Dongnae dongnae = Dongnae.builder().id(1L).gu("서울구").dong("서울동").city("서울시").townName("무슨마을").build();
    User user = User.builder().id(1L).age(Age.AGE10).email("email").dongnae(dongnae).gender(Gender.FEMALE).infoCert(YesNo.NO).townCert(YesNo.NO).townCertCnt(10).id(1L).kakaoId(90L).nickname("nickname").refreshToken("refreshToken").build();


    @Autowired
    private SharingBoardRepository sharingBoardRepository;

    /*
     * [가계부 공유] 게시글 검
     * Pageable : page, size, sortBy
     * @param sort
     */
    public List<SharingDto.ListResponse> searchByKeyword(String keyword, int category, Pageable pageable) {
        sharingBoardRepository
    }

}
