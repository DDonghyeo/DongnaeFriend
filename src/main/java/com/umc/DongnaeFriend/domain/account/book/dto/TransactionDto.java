package com.umc.DongnaeFriend.domain.account.book.dto;

import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.account.book.entity.Memo;
import com.umc.DongnaeFriend.domain.account.book.entity.Transaction;
import com.umc.DongnaeFriend.domain.type.PayCategory;
import com.umc.DongnaeFriend.domain.type.TransactionCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransactionRequest{
        private Integer year;
        private Integer month;
        private Integer day;
        private Integer type;
        private Long price;
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


    @Getter
    @NoArgsConstructor
    public static class TransactionByCategory{
        private TransactionCategory transactionCategory;
        private Long price;
        private Long expenditure;
        private Long income;
        private Long budget;

        public TransactionByCategory(TransactionCategory transactionCategory, Long price, Long expenditure, Long income, Long budget) {
            this.transactionCategory = transactionCategory;
            this.price = price;
            this.expenditure = expenditure;
            this.income = income;
            this.budget = budget;
        }
    }
}
