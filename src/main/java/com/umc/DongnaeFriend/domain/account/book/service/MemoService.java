package com.umc.DongnaeFriend.domain.account.book.service;

import com.umc.DongnaeFriend.domain.account.book.dto.MemoDto;
import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.account.book.entity.Memo;
import com.umc.DongnaeFriend.domain.account.book.repository.accountBook.AccountBookRepository;
import com.umc.DongnaeFriend.domain.account.book.repository.memo.MemoRepository;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final AccountBookRepository accountBookRepository;

    // 메모 전체 조회 -> 리스트
    public MemoDto.MemoListResponse getMemoList(Integer year, Integer month){

        AccountBook accountBook = accountBookRepository.findByYearAndMonth(year, month)
                .orElseThrow(() ->  new CustomException(ErrorCode.NO_CONTENT_FOUND));

        List<Memo> memoList = memoRepository.findMemoListByAccountBookId(accountBook.getId());
        return MemoDto.MemoListResponse.of(memoList);
    }

    // 메모 등록
    @Transactional
    public void createMemo(MemoDto.MemoRequest memoRequest, Integer year, Integer month){

        // User 권한 확인
        AccountBook accountBook = accountBookRepository.findByYearAndMonth(year, month)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_CONTENT_FOUND));
        Integer memoCnt = memoRepository.getMemoCnt(year, month);

        if(memoCnt<8){
            memoRepository.save(memoRequest.toEntity(accountBook));
        }else{
            // 개수 초과 예외처리
            throw new CustomException(ErrorCode.MEMO_LIMIT);
        }
    }

    // 메모 수정
    @Transactional
    public void updateMemo(MemoDto.MemoRequest memoRequest, Long memoId){

        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_CONTENT_FOUND));
        memo.updateMemo(memoRequest);
    }

    // 메모 삭제
    @Transactional
    public void deleteMemo(Long memoId){

        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_CONTENT_FOUND));
        memoRepository.deleteById(memo.getId());
    }
}
