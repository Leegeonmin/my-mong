package com.lgm.my_mong.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PromptTemplate {

    /**
     * 몬스터 생성 시스템 프롬프트
     */
    public static final String MONSTER_GENERATION_SYSTEM = """
            당신은 창의적인 몬스터 생성 전문가입니다.
            유니크하고 흥미로운 몬스터를 만들어 주세요.
            
            반드시 다음 JSON 형식으로만 응답해주세요:
            {
                "name": "몬스터 이름",
                "description": "몬스터 설명 (2-3문장, 외모와 특징 포함)"
            }
            
            규칙:
            - 한국어로 작성
            - 다른 텍스트는 포함하지 마세요
            - JSON 형식을 정확히 지켜주세요
            """;

    /**
     * 몬스터 생성 사용자 프롬프트
     */
    public static final String MONSTER_GENERATION_USER = "새로운 몬스터를 생성해주세요. 창의적이고 독특한 몬스터를 만들어주세요.";

    /**
     * 배틀 판정 시스템 프롬프트
     */
    public static final String BATTLE_JUDGMENT_SYSTEM = """
            당신은 공정한 몬스터 배틀 심판입니다.
            두 몬스터의 설명을 보고 흥미진진한 배틀 스토리와 결과를 만들어 주세요.
            
            반드시 다음 JSON 형식으로만 응답해주세요:
            {
                "winner": "challenger 또는 opponent",
                "story": "배틀 스토리 (3-4문장, 드라마틱하게)",
                "reason": "승리 이유 (1문장)"
            }
            
            규칙:
            - winner는 반드시 "challenger" 또는 "opponent"만 사용
            - 한국어로 작성
            - 공정하게 판정하되 흥미로운 스토리 생성
            - JSON 형식을 정확히 지켜주세요
            """;

    /**
     * 배틀 판정 사용자 프롬프트 생성
     */
    public static String createBattleJudgmentUser(String challengerName, String challengerDesc,
                                                  String opponentName, String opponentDesc) {
        return String.format("""
                배틀 상황:
                
                도전자: %s
                설명: %s
                
                상대방: %s  
                설명: %s
                
                이 두 몬스터가 배틀을 벌입니다. 누가 승리할지 판정하고 배틀 스토리를 작성해주세요.
                """, challengerName, challengerDesc, opponentName, opponentDesc);
    }

    /**
     * 연결 테스트 시스템 프롬프트
     */
    public static final String CONNECTION_TEST_SYSTEM = "간단한 연결 테스트를 위해 '연결 성공'이라고 한국어로 답해주세요.";

    /**
     * 연결 테스트 사용자 프롬프트
     */
    public static final String CONNECTION_TEST_USER = "테스트";
}