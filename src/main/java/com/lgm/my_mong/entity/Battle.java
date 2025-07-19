package com.lgm.my_mong.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Table(name = "battles")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Battle extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenger_mong_id", nullable = false)
    private Mong challengerMong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opponent_mong_id", nullable = false)
    private Mong opponentMong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_mong_id")
    private Mong winnerMong;

    // AI가 생성한 배틀 스토리
    @Column(name = "battle_story", columnDefinition = "TEXT")
    private String battleStory;

    // 배틀 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BattleStatus status = BattleStatus.PENDING;


    // 배틀 완료 시간 (별도 필드로 유지)
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    // 배틀 상태 enum
    public enum BattleStatus {
        PENDING,    // 배틀 진행 중
        COMPLETED,  // 배틀 완료
        CANCELLED   // 배틀 취소 (필요시)
    }
}