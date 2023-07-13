package com.umc.DongnaeFriend.domain.account.sharing.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.umc.DongnaeFriend.domain.BaseTimeEntity;
import com.umc.DongnaeFriend.domain.type.SharingCategory;
import com.umc.DongnaeFriend.domain.user.entity.User;
import javax.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity
public class SharingBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "sharing_board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private SharingCategory category;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;
}
