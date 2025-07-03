package com.lgm.my_mong.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false)
    private Integer rating = 1000;  // ELO 랭킹 점수 기본값

    // 1:1 관계 - 한 유저는 최대 1마리 몬스터 소유
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Mong monster;

    // 편의 메서드
    public boolean hasMonster() {
        return monster != null;
    }

    public void updateRating(int newRating) {
        this.rating = newRating;
    }
}