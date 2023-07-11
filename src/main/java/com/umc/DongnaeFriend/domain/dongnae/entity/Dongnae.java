package com.umc.DongnaeFriend.domain.dongnae.entity;

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
public class Dongnae extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "dongnae_id")
    private Long id;

    @Column(nullable = false)
    private String townName;

    @Column(nullable = false)
    private String city;  //시

    @Column(nullable = false)
    private String gu; // 구

    @Column(nullable = false)
    private String dong; // 동
}
