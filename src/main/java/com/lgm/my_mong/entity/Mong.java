package com.lgm.my_mong.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mongs")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mong extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", unique = true, nullable = false)
    private User owner;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String name;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    // 편의 메서드
    public String getOwnerNickname() {
        return owner != null ? owner.getNickname() : "Unknown";
    }

    public Integer getOwnerRating() {
        return owner != null ? owner.getRating() : 0;
    }
}
