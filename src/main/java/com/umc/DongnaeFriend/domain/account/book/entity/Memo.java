package com.umc.DongnaeFriend.domain.account.book.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.umc.DongnaeFriend.domain.BaseTimeEntity;
import javax.persistence.*;

import com.umc.DongnaeFriend.domain.account.book.dto.MemoDto;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity
public class Memo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "memo_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_book_id", nullable = false)
    private AccountBook accountBook;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String memo;

    @Column(nullable = false)
    private Boolean done;

    public void updateMemo(MemoDto.MemoRequest request){
        this.memo = request.getMemo();
        this.done = request.getDone();
    }
}
