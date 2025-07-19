package com.lgm.my_mong.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgm.my_mong.dto.MongDTO;
import com.lgm.my_mong.dto.OpenAiResponseDTO;
import com.lgm.my_mong.exception.CustomException;
import com.lgm.my_mong.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAiProvider {

    private final RestClient openAiRestClient;
    ObjectMapper om = new ObjectMapper();
    @Value("${spring.openai.api.model}")
    private String model;

    @Value("${spring.openai.api.max-tokens}")
    private int maxTokens;

    @Value("${spring.openai.api.temperature}")
    private float temperature;

    public OpenAiResponseDTO sendRequest(String systemMessage, String userMessage) {
        Map<String, Object> request = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "system", "content", systemMessage),
                        Map.of("role", "user", "content", userMessage)
                ),
                "max_tokens", maxTokens,
                "temperature", temperature
        );
        try {
            OpenAiResponseDTO result = openAiRestClient
                    .post()
                    .uri("/chat/completions")
                    .body(request)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        throw new CustomException(ResponseCode.OPENAI_400_ERROR);
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new CustomException(ResponseCode.OPENAI_500_ERROR);
                    })
                    .body(OpenAiResponseDTO.class);

            return result;

        }
        // 응답 에러 시 처리 로직 구현해야함!!
        catch (Exception e) {
            throw new CustomException(ResponseCode.OPENAI_ERROR);
        }
    }

    public MongDTO generateMonster()  {
        String systemMessage = "당신은 창의적인 몬스터 생성 전문가입니다. 우스꽝스러워도 되고 진지해도 됩니다. JSON 형식으로 답변하는데 들어가는 데이터는" +
                "이름, 수식어, 스토리(설명)입니다";
        String userMessage = "새로운 몬스터를 생성해주세요. 한국어로 작성해주세요.";

            OpenAiResponseDTO response = this.sendRequest(systemMessage, userMessage);
            String content = response.getChoices().get(0).getMessage().getContent();

            // JSON 문자열에서 실제 JSON 부분만 추출
            String jsonString = extractJsonFromContent(content);

            // JSON을 MonsterDTO로 파싱
        try {
            return om.readValue(jsonString, MongDTO.class);

        }catch (JsonProcessingException e) {
            throw new CustomException(ResponseCode.JSON_PARSING_ERROR);
        }

    }

//    public BattleResultDTO battleMonsters(String challenger, String opponent) {
//        String systemMessage = "당신은 공정한 몬스터 배틀 심판입니다. JSON 형식으로 답변해주세요.";
//        String userMessage = String.format("배틀: %s vs %s. 누가 승리할지 판정해주고 배틀 스토리 라인 짜주세요.", challenger, opponent);
//
//        try {
//            OpenAiResponseDTO response = this.sendRequest(systemMessage, userMessage);
//            String content = response.getChoices().get(0).getMessage().getContent();
//
//            // JSON 문자열에서 실제 JSON 부분만 추출
//            String jsonString = extractJsonFromContent(content);
//
//            // JSON을 MonsterDTO로 파싱
//            return om.readValue(jsonString, BattleResultDTO.class);
//
//        } catch (Exception e) {
//            log.error("몬스터 생성 중 오류 발생", e);
//            throw new RuntimeException("몬스터 생성에 실패했습니다.", e);
//        }
//    }
    private String extractJsonFromContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("응답 내용이 비어있습니다.");
        }

        // 방법 1: ```json으로 둘러싸인 부분 추출
        Pattern jsonPattern = Pattern.compile("```json\\s*\\n(.*?)\\n```", Pattern.DOTALL);
        Matcher matcher = jsonPattern.matcher(content);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        // 방법 2: 단순히 ```로 둘러싸인 부분 추출
        Pattern simplePattern = Pattern.compile("```\\s*\\n(.*?)\\n```", Pattern.DOTALL);
        Matcher simpleMatcher = simplePattern.matcher(content);

        if (simpleMatcher.find()) {
            return simpleMatcher.group(1).trim();
        }

        // 방법 3: { }로 둘러싸인 JSON 추출
        Pattern bracePattern = Pattern.compile("(\\{.*\\})", Pattern.DOTALL);
        Matcher braceMatcher = bracePattern.matcher(content);

        if (braceMatcher.find()) {
            return braceMatcher.group(1).trim();
        }

        // 추출 실패 시 원본 반환
        log.warn("JSON 추출 실패, 원본 내용 반환: {}", content);
        return content;
    }
}