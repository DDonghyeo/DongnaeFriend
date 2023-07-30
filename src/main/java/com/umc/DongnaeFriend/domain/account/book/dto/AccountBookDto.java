package com.umc.DongnaeFriend.domain.account.book.dto;

import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.type.TransactionCategory;
import lombok.*;

import java.util.List;

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
        private List<Expense> expense;
    }
}
