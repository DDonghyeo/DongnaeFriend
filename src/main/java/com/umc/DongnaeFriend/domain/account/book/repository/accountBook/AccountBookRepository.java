package com.umc.DongnaeFriend.domain.account.book.repository.accountBook;

import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.account.book.service.AccountBookService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long>, AccountBookRepositoryCustom {

    // 지난 달 지출액 확인

    Optional<AccountBook> findByIdAndYearAndMonth(Long accountBookId, Integer year, Integer month);
    Optional<AccountBook> findByYearAndMonth(Integer year, Integer month);
}
