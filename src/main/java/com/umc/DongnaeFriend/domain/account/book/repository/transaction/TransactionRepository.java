package com.umc.DongnaeFriend.domain.account.book.repository.transaction;

import com.umc.DongnaeFriend.domain.account.book.dto.TransactionDto;
import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.account.book.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepositoryCustom {

    @Query(value = "select t from Transaction t where t.year = :year and t.month = :month")
    List<Transaction> findByYearAndMonth(@Param("year") Integer year, @Param("month") Integer month);

/*    @Query(value = "select new com.umc.DongnaeFriend.domain.account.book.dto.TransactionDto.TransactionByCategory(tr.transactionCategory, SUM(tr.price)) " +
            "from Transaction tr " +
            "where tr.year = :year and tr.month = :month " +
            "group by tr.transactionCategory")
    TransactionDto.TransactionByCategory getTransactionByYearAndMonth(@Param("year") Integer year, @Param("month") Integer month);*/
}
