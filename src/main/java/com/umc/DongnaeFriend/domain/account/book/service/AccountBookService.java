package com.umc.DongnaeFriend.domain.account.book.service;

import com.umc.DongnaeFriend.domain.account.book.dto.AccountBookDto;
import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.account.book.repository.accountBook.AccountBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AccountBookService {

    // User 권한 확인 필요 //

    private final AccountBookRepository accountBookRepository;


    // 가계부 예산 설정 (한달)
    @Transactional
    public void createBudget(Integer year, Integer month, Long budget){
        accountBookRepository.save(AccountBookDto.BudgetRequest.toEntity(year, month, budget));
    }

    // 가계부 예산 설정 조회
    public AccountBookDto.BudgetResponse getBudget(Integer year, Integer month){
        AccountBook accountBook = accountBookRepository.findByYearAndMonth(year, month).orElseThrow();
        return AccountBookDto.BudgetResponse.of(accountBook.getId(),accountBook.getBudget());
    }

    // 가계부 예산 설정 수정
    public void updateBudget(Integer year, Integer month, Long budget){
        AccountBook accountBook = accountBookRepository.findByYearAndMonth(year, month).orElseThrow();
        accountBook.updateBudget(budget);
    }


    // 가계부 조회 -> 이번달 남은 예산 & 지출, 저축(수입), 카테고리별 지출
    public AccountBookDto.AccountBookResponse getAccountBookResponse(Integer year, Integer month) {
        AccountBook accountBook = accountBookRepository.findByYearAndMonth(year, month).orElseThrow();
        return AccountBookDto.AccountBookResponse.builder()
                .income(accountBook.getIncome())
                .expenditure(accountBook.getExpenditure())
                .budget(accountBook.getBudget()+accountBook.getIncome()-accountBook.getExpenditure())
                .expense(accountBookRepository.getAccountBook(year,month))
                .build();
    }
}
