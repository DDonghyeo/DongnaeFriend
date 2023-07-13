package com.umc.DongnaeFriend.domain.dongnae.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.umc.DongnaeFriend.domain.BaseTimeEntity;
import com.umc.DongnaeFriend.domain.type.DongnaeBoardCategory;
import com.umc.DongnaeFriend.domain.user.entity.User;
import javax.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity
public class DongnaeBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "dongnae_board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dongnae_id", nullable = false)
    private Dongnae dongnae;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private DongnaeBoardCategory category;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;

    private String place;   // 사용자 장소 공유시 장소 이름(ex. "00키친")
    private String placeLocation;  // 장소의 정확한 위치
}
