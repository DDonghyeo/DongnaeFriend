package com.umc.DongnaeFriend.domain.account.book.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.account.book.entity.Memo;
import com.umc.DongnaeFriend.domain.type.TransactionCategory;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class AccountBookDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountBookCategoryResponse{
        private TransactionCategory transactionCategory;
        private Long price;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BudgetResponse{
        private Long accountBookId;
        private Long budget;

        public static BudgetResponse of(Long accountBookId, Long budget){
            return new BudgetResponse(accountBookId, budget);
        }
    }

    @Getter
    public static class BudgetRequest{
        public static AccountBook toEntity(Integer year, Integer month, Long amount){
            return AccountBook.builder()
                    .expenditure(0L)
                    .income(0L)
                    .budget(amount)
                    .year(year)
                    .month(month)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountBookResponse {
        private Long expenditure;
        private Long income;
        private Long budget;

        private ExpenseDto expenseDto;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExpenseDto{
        private Long food;
        private Long transport;
        private Long culture;
        private Long daily;
        private Long trade;
        private Long beauty;
        private Long health;
        private Long education;
        private Long fix;
        private Long OTT;
        private Long etc;
    }
}
