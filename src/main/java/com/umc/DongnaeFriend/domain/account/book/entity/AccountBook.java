package com.umc.DongnaeFriend.domain.account.book.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.umc.DongnaeFriend.domain.BaseTimeEntity;
import com.umc.DongnaeFriend.domain.user.entity.User;
import javax.persistence.*;
import lombok.*;;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity
public class AccountBook extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "account_book_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long budget;

    @Column(nullable = false)
    private Long expenditure;

    @Column(nullable = false)
    private Long income;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;
}
