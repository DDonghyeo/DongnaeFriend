package com.umc.DongnaeFriend.domain.account.book.dto;

import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AccountBookDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountBookResponse{
        private Long accountBookId;
        private Long expenditure;
        private Long income;
        private Long budget;
        private Integer month;
        private Integer year;

        public static AccountBookResponse of(Long accountBookId, Long expenditure, Long income,
                                        Long nowBudget, Integer month, Integer year){
            return AccountBookResponse.builder()
                    .accountBookId(accountBookId)
                    .expenditure(expenditure)
                    .income(income)
                    .budget(nowBudget-expenditure)
                    .month(month)
                    .year(year)
                    .build();
        }
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
                    .budget(amount)
                    .year(year)
                    .month(month)
                    .build();
        }
    }
}
