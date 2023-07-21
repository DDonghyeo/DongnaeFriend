package com.umc.DongnaeFriend.domain.account.book.repository.accountBook;

import com.querydsl.jpa.impl.JPAQueryFactory;

import com.umc.DongnaeFriend.domain.account.book.dto.Expense;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AccountBookRepositoryCustomImpl implements AccountBookRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Expense> getAccountBook (@Param("year") Integer year,@Param("month") Integer month) {

        return em.createQuery(
                        "select new com.umc.DongnaeFriend.domain.account.book.dto.Expense(t.transactionCategory, sum(t.price))" +
                                "from Transaction t where t.year= :year and t.month= :month group by t.transactionCategory", Expense.class)
                .setParameter("year", year)
                .setParameter("month", month)
                .getResultList();
    }
}

//                .select(transaction.transactionCategory, transaction.price.sum())
//                .from(transaction)
//                .where(transaction.month.eq(month).and(transaction.year.eq(year)))
//                .groupBy(transaction.transactionCategory)
//                .fetch().toArray();
