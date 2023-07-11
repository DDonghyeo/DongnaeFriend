package com.umc.DongnaeFriend.domain.account.sharing.entity;

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
public class SharingImg extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "sb_img_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sharing_board_id", nullable = false)
    private SharingBoard sharingBoard;

    @Column(nullable = false)
    private String imageUrl;
}
