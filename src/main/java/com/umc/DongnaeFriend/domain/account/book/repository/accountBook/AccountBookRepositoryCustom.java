package com.umc.DongnaeFriend.domain.account.book.repository.accountBook;


import com.querydsl.core.Tuple;
import com.umc.DongnaeFriend.domain.account.book.dto.AccountBookDto;

import java.util.List;

public interface AccountBookRepositoryCustom {
    List<AccountBookDto.AccountBookCategoryResponse> getAccountBook(Integer year, Integer month);
}
