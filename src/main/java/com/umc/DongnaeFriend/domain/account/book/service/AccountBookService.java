package com.umc.DongnaeFriend.domain.account.book.service;

import com.umc.DongnaeFriend.domain.account.book.dto.AccountBookDto;
import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.account.book.repository.accountBook.AccountBookRepository;
import com.umc.DongnaeFriend.domain.dongnae.entity.Dongnae;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeRepository;
import com.umc.DongnaeFriend.domain.type.Age;
import com.umc.DongnaeFriend.domain.type.Gender;
import com.umc.DongnaeFriend.domain.type.YesNo;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;
    private final UserRepository userRepository;

    // 가계부 예산 설정 (한달)
    @Transactional
    public void createBudget(Integer year, Integer month, Long budget){
        User user = findUser();
        if (accountBookRepository.findByYearAndMonthAndUser(year, month, user).isEmpty()) {
            accountBookRepository.save(AccountBookDto.BudgetRequest.toEntity(year, month, budget, user));
        }else{
            throw new CustomException(ErrorCode.ACCOUNTBOOK_ALREADY_EXISTS);
        }
    }

    // 가계부 예산 설정 조회
    public AccountBookDto.BudgetResponse getBudget(Integer year, Integer month){
        User user = findUser();
        AccountBook accountBook = accountBookRepository.findByYearAndMonthAndUser(year, month, user)
                .orElseThrow(() ->  new CustomException(ErrorCode.NO_CONTENT_FOUND));
        return AccountBookDto.BudgetResponse.of(accountBook.getId(),accountBook.getBudget());
    }

    // 가계부 예산 설정 수정
    @Transactional
    public void updateBudget(Integer year, Integer month, Long budget){
        User user = findUser();

        AccountBook accountBook = accountBookRepository.findByYearAndMonthAndUser(year, month, user)
                .orElseThrow(() ->  new CustomException(ErrorCode.NO_CONTENT_FOUND));

        if (!Objects.equals(accountBook.getUser().getId(), user.getId())) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }
        accountBook.updateBudget(budget);
    }


    // 가계부 조회 -> 이번달 남은 예산 & 지출, 저축(수입), 카테고리별 지출
    public AccountBookDto.AccountBookResponse getAccountBookResponse(Integer year, Integer month) {
        User user = findUser();

        AccountBook accountBook = accountBookRepository.findByYearAndMonthAndUser(year, month, user)
                .orElseThrow(() ->  new CustomException(ErrorCode.NO_CONTENT_FOUND));

        if (!Objects.equals(accountBook.getUser().getId(), user.getId())) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }
        return AccountBookDto.AccountBookResponse.builder()
                .income(accountBook.getIncome())
                .expenditure(accountBook.getExpenditure())
                .budget(accountBook.getBudget()+accountBook.getIncome()-accountBook.getExpenditure())
                .expense(accountBookRepository.getAccountBook(year,month))
                .build();
    }

    public User findUser() {
        Object userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findById((Long) userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
