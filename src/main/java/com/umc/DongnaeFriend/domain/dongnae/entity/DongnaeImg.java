package com.umc.DongnaeFriend.domain.dongnae.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.umc.DongnaeFriend.domain.BaseTimeEntity;
import javax.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity
public class DongnaeImg extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "db_img_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dongnae_board_id", nullable = false)
    private DongnaeBoard dongnaeBoard;

    @Column(nullable = false)
    private String imageUrl;
}
