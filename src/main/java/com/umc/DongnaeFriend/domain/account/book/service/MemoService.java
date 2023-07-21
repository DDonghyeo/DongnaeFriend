package com.umc.DongnaeFriend.domain.account.book.service;

import com.umc.DongnaeFriend.domain.account.book.dto.MemoDto;
import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.account.book.entity.Memo;
import com.umc.DongnaeFriend.domain.account.book.repository.accountBook.AccountBookRepository;
import com.umc.DongnaeFriend.domain.account.book.repository.memo.MemoRepository;
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

        // User 권한 확인
        AccountBook accountBook = accountBookRepository.findByYearAndMonth(year, month).orElseThrow();
        List<Memo> memoList = memoRepository.findMemoListByAccountBookId(accountBook.getId());
        return MemoDto.MemoListResponse.of(memoList);
    }

    // 메모 등록
    @Transactional
    public void createMemo(MemoDto.MemoRequest memoRequest, Integer year, Integer month){

        // User 권한 확인
        AccountBook accountBook = accountBookRepository.findByYearAndMonth(year, month).orElseThrow();
        Integer memoCnt = memoRepository.getMemoCnt(year, month);

        if(memoCnt<8){
            memoRepository.save(memoRequest.toEntity(accountBook));
        }else{
            // 개수 초과 예외처리
            log.info("메모 개수 초과 발생 !");
        }
    }

    // 메모 수정
    @Transactional
    public void updateMemo(MemoDto.MemoRequest memoRequest, Long memoId){

        // User 권한 확인
        Memo memo = memoRepository.findById(memoId).orElseThrow();
        memo.updateMemo(memoRequest);
    }

    // 메모 삭제
    @Transactional
    public void deleteMemo(Long memoId){

        // User 권한 확인
        Memo memo = memoRepository.findById(memoId).orElseThrow();
        memoRepository.deleteById(memo.getId());
    }
}
