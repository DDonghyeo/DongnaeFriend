package com.umc.DongnaeFriend.domain.account.book.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.umc.DongnaeFriend.domain.BaseTimeEntity;
import com.umc.DongnaeFriend.domain.account.book.dto.AccountBookDto;
import com.umc.DongnaeFriend.domain.account.book.dto.TransactionDto;
import com.umc.DongnaeFriend.domain.type.PayCategory;
import com.umc.DongnaeFriend.domain.type.TransactionCategory;
import javax.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity
public class Transaction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_book_id", nullable = false)
    private AccountBook accountBook;

    @Column(nullable = false)
    private Integer type;   // 0이면 지출, 1이면 수입

    @Column(nullable = false)
    private Long price;

    @Enumerated(value = STRING)
    private PayCategory payCategory;  //현금, 체크카드 등

    @Enumerated(value = STRING)
    //@Column(nullable = false)
    private TransactionCategory transactionCategory;  //식비, 교육 등

    @Column(columnDefinition = "MEDIUMTEXT")
    private String categoryMemo;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer day;

    public void updateTransaction(TransactionDto.TransactionRequest request, AccountBook accountBook){
        this.accountBook = accountBook;
        this.categoryMemo = request.getCategoryMemo();
        this.year = request.getYear();
        this.month =request.getMonth();
        this.day = request.getDay();
        this.price = request.getPrice();
        this.payCategory = request.getPayCategory();

    }
}
