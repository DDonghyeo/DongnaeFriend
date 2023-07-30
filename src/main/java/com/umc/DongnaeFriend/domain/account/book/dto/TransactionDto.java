package com.umc.DongnaeFriend.domain.account.book.dto;

import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.account.book.entity.Memo;
import com.umc.DongnaeFriend.domain.account.book.entity.Transaction;
import com.umc.DongnaeFriend.domain.type.PayCategory;
import com.umc.DongnaeFriend.domain.type.TransactionCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransactionRequest{

        @NotNull(message = "년도는 필수입니다")
        private Integer year;

        @NotNull(message = "월은 필수입니다")
        private Integer month;

        @NotNull(message = "일은 필수입니다")
        private Integer day;

        @NotNull(message = "지출 또는 수입을 선택해주세요")
        private Integer type;

        @NotNull(message = "금액은 필수입니다")
        private Long price;

        @NotNull(message = "카테고리는 필수입니다")
        private TransactionCategory transactionCategory;

        private PayCategory payCategory;

        private String categoryMemo;

        public Transaction toEntity(AccountBook accountBook){
            return Transaction.builder()
                    .type(type) // 0이면 지출, 1이면 수입
                    .transactionCategory(transactionCategory)
                    .year(year)
                    .month(month)
                    .day(day)
                    .price(price)
                    .payCategory(payCategory)
                    .categoryMemo(categoryMemo)
                    .accountBook(accountBook)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransactionResponse{
        private Long id;
        private Integer type;
        private TransactionCategory transactionCategory;
        private Integer year;
        private Integer month;
        private Integer day;
        private Long price;
        private PayCategory payCategory;
        private String memo;

        public static TransactionResponse from(Transaction transaction){
            return new TransactionResponse(
                    transaction.getId(),
                    transaction.getType(),
                    transaction.getTransactionCategory(),
                    transaction.getYear(),
                    transaction.getMonth(),
                    transaction.getDay(),
                    transaction.getPrice(),
                    transaction.getPayCategory(),
                    transaction.getCategoryMemo());
        }
    }

    @Getter
    public static class TransactionListResponse{
        private List<TransactionResponse> transactions;

        public TransactionListResponse(List<TransactionDto.TransactionResponse> transactions){
            this.transactions = transactions;
        }

        public static TransactionListResponse of(List<Transaction> transactionList){
            List<TransactionResponse> transactionResponses  = transactionList
                    .stream()
                    .map(TransactionResponse::from)
                    .collect(Collectors.toList());

            return new TransactionListResponse(transactionResponses);
        }
    }
}
