package com.umc.DongnaeFriend.domain.account.book.repository.memo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.DongnaeFriend.domain.account.book.entity.Memo;
import com.umc.DongnaeFriend.domain.account.book.entity.QAccountBook;
import com.umc.DongnaeFriend.domain.account.book.entity.QMemo;

import static org.springframework.data.jpa.domain.Specification.where;

public class MemoRepositoryCustomImpl implements MemoRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public MemoRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Integer getMemoCnt(Integer year, Integer month) {
        QMemo memo = QMemo.memo1;
        QAccountBook accountBook = QAccountBook.accountBook;
        return Math.toIntExact(jpaQueryFactory
                .select(memo.count())
                .from(memo)
                .innerJoin(memo.accountBook, accountBook)
                .where(memo.accountBook.month.eq(month)
                        .and(accountBook.year.eq(year)))
                .fetchFirst());
    }
}
