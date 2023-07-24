package com.umc.DongnaeFriend.domain.user.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.umc.DongnaeFriend.domain.BaseTimeEntity;
import com.umc.DongnaeFriend.domain.dongnae.entity.Dongnae;
import com.umc.DongnaeFriend.domain.type.Age;
import com.umc.DongnaeFriend.domain.type.Gender;
import com.umc.DongnaeFriend.domain.type.YesNo;
import javax.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity
@Table(name = "users")
public class  User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dongnae_id")
    private Dongnae dongnae;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    private String profileImage;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private YesNo townCert;

    @Column(nullable = false)
    private Integer townCertCnt;  // 동네 인증 횟수

    @Enumerated(value = STRING)
    private Gender gender;

    @Enumerated(value = STRING)
    private Age age;

    @Enumerated(value = STRING)
    private YesNo infoCert;   // 개인정보(성별, 나이대 등) 공개 여부

    private String refreshToken;

    private Long kakaoId;
}
