package com.umc.DongnaeFriend.domain.account.book.repository.accountBook;

import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long>, AccountBookRepositoryCustom {

    Optional<AccountBook> findByYearAndMonthAndUser(Integer year, Integer month, User user);
    @Modifying
    @Query(value = "update AccountBook ab "
            + "set ab.expenditure = ab.expenditure + :expenditure "
            + "where ab.id = :accountBookId")
    void updateAccountBookExpenditure(@Param("accountBookId")Long accountBookId, @Param("expenditure")Long expenditure);

    @Modifying
    @Query(value = "update AccountBook ab "
            + "set ab.income = ab.income + :income "
            + "where ab.id = :accountBookId")
    void updateAccountBookIncome(@Param("accountBookId")Long accountBookId, @Param("income")Long income);

    @Modifying
    @Query(value = "update AccountBook ab "
            + "set ab.expenditure = ab.expenditure - :expenditure "
            + "where ab.id = :accountBookId")
    void updateAccountBookExpenditureDelete(@Param("accountBookId")Long accountBookId, @Param("expenditure")Long expenditure);
    @Modifying
    @Query(value = "update AccountBook ab "
            + "set ab.income = ab.income - :income "
            + "where ab.id = :accountBookId")
    void updateAccountBookIncomeDelete(@Param("accountBookId")Long accountBookId, @Param("income")Long income);
    @Modifying
    @Query(value = "update AccountBook ab "
            + "set ab.expenditure = ab.expenditure - :expenditureGap "
            + "where ab.id = :accountBookId")
    void updateAccountBookExpenditureEdit(@Param("accountBookId")Long accountBookId, @Param("expenditureGap")Long expenditureGap);
    @Modifying
    @Query(value = "update AccountBook ab "
            + "set ab.income = ab.income + :incomeGap "
            + "where ab.id = :accountBookId")
    void updateAccountBookIncomeEdit(@Param("accountBookId")Long accountBookId, @Param("incomeGap")Long incomeGap);

    /*@Query(value = "SELECT transaction.transaction_category, SUM(transaction.price) as sum_price " +
            "from transaction " +
            "where transaction.month = :month and transaction.year = :year " +
            "group by transaction.transaction_category", nativeQuery = true)
    Object[] getAccountBookGroupByCategory (@Param("month") long month, @Param("year") long year);*/
}
