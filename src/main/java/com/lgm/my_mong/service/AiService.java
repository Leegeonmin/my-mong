package com.lgm.my_mong.service;

import com.lgm.my_mong.dto.OpenAiResponseDTO;
import com.lgm.my_mong.provider.OpenAiProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {

    private final OpenAiProvider openAiProvider;

    public String generateMonster() {
        String systemMessage = "당신은 창의적인 몬스터 생성 전문가입니다. JSON 형식으로 답변해주세요.";
        String userMessage = "새로운 몬스터를 생성해주세요. 한국어로 작성해주세요.";

        OpenAiResponseDTO response = openAiProvider.sendRequest(systemMessage, userMessage);
        return response.getChoices().get(0).getMessage().getContent();
    }

    public String judgeBattle(String challenger, String opponent) {
        String systemMessage = "당신은 공정한 몬스터 배틀 심판입니다. JSON 형식으로 답변해주세요.";
        String userMessage = String.format("배틀: %s vs %s. 누가 승리할지 판정해주세요.", challenger, opponent);

        OpenAiResponseDTO response = openAiProvider.sendRequest(systemMessage, userMessage);
        return response.getChoices().get(0).getMessage().getContent();
    }

    public String testConnection() {
        String systemMessage = "간단한 테스트입니다.";
        String userMessage = "안녕하세요";

        OpenAiResponseDTO response = openAiProvider.sendRequest(systemMessage, userMessage);
        return response.getChoices().get(0).getMessage().getContent();
    }
}