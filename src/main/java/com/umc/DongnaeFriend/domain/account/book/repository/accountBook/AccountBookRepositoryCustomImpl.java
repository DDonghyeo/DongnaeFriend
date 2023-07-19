package com.umc.DongnaeFriend.domain.account.book.repository.accountBook;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountBookRepositoryCustomImpl implements AccountBookRepositoryCustom {

    private final JPAQueryFactory queryFactory;


}
