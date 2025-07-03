package com.lgm.my_mong.entity;
import jakarta.persistence.*;
import lombok.*;

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

    // 배틀 신청자 (이 사람만 랭킹에 영향받음)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenger_id", nullable = false)
    private User challenger;

    // 배틀 상대방 (수동적 참여, 랭킹 영향 없음)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opponent_id", nullable = false)
    private User opponent;

    // 배틀 승자 (nullable - 배틀 완료 후 설정)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private User winner;

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

    // 편의 메서드
    public boolean isCompleted() {
        return status == BattleStatus.COMPLETED && winner != null;
    }

    public boolean isChallengerWinner() {
        return winner != null && winner.equals(challenger);
    }

    public boolean isOpponentWinner() {
        return winner != null && winner.equals(opponent);
    }

    public User getLoser() {
        if (winner == null) return null;
        return winner.equals(challenger) ? opponent : challenger;
    }

    // challenger 기준으로 승패 확인 (랭킹 계산용)
    public boolean isChallengerVictory() {
        return isCompleted() && winner.equals(challenger);
    }

    public void completeBattle(User winner, String battleStory) {
        this.winner = winner;
        this.battleStory = battleStory;
        this.status = BattleStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }
    
    // 배틀 상태 enum
    public enum BattleStatus {
        PENDING,    // 배틀 진행 중
        COMPLETED,  // 배틀 완료
        CANCELLED   // 배틀 취소 (필요시)
    }
}