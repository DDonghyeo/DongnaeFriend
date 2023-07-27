package com.umc.DongnaeFriend.domain.account.book.repository.accountBook;


import com.umc.DongnaeFriend.domain.account.book.dto.Expense;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountBookRepositoryCustom {
    List<Expense> getAccountBook(Integer year, Integer month);
}
