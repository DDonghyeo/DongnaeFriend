package com.umc.DongnaeFriend.domain.account.book.repository.memo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.umc.DongnaeFriend.domain.account.book.entity.QAccountBook.accountBook;
import com.umc.DongnaeFriend.domain.account.book.entity.QMemo;


public class MemoRepositoryCustomImpl implements MemoRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public MemoRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Integer getMemoCnt(Integer year, Integer month) {
        QMemo memo = QMemo.qMemo;

        return Math.toIntExact(jpaQueryFactory
                .select(memo.count())
                .from(memo)
                .innerJoin(memo.accountBook, accountBook)
                .where(memo.accountBook.month.eq(month)
                        .and(accountBook.year.eq(year)))
                .fetchFirst());
    }
}
