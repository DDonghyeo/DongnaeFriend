package com.umc.DongnaeFriend.domain.account.book.repository.accountBook;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.umc.DongnaeFriend.domain.account.book.entity.QTransaction.transaction;

import com.umc.DongnaeFriend.domain.account.book.dto.AccountBookDto;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AccountBookRepositoryCustomImpl implements AccountBookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public AccountBookDto.ExpenseDto getAccountBook(Integer year, Integer month) {

        List<Tuple> result = queryFactory
                .select(transaction.transactionCategory, transaction.price.sum())
                .from(transaction)
                .where(transaction.month.eq(month).and(transaction.year.eq(year)))
                .groupBy(transaction.transactionCategory)
                .fetch();

        AccountBookDto.ExpenseDto accountBookList = new AccountBookDto.ExpenseDto();

        for (Tuple tuple : result) {
            AccountBookDto.AccountBookCategoryResponse response
                    = AccountBookDto.AccountBookCategoryResponse.builder()
                    .price(tuple.get(transaction.price.sum()))
                    .transactionCategory(tuple.get(transaction.transactionCategory))
                    .build();
            accountBookList.
        }
        return accountBookList;
    }
}
