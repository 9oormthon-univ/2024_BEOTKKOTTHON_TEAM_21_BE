package com.teamkrews.openAI.service;

import com.teamkrews.message.model.request.MessageDTO;
import com.teamkrews.openAI.model.request.MessageTranslatorDTO;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.json.JSONArray;

@RequiredArgsConstructor
@Service
public class MessageTranslatorService {

    @Value("${openai.url.prompt}")
    private String url;

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    // 말투 변환
    public String transformMessage(MessageDTO messageDTO) {

        final String content = messageDTO.getContent();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4");
        requestBody.put("temperature", 0.8);
        requestBody.put("max_tokens", 4096);
        requestBody.put("top_p", 1);
        requestBody.put("messages", new Object[] {
                new HashMap<String, String>() {{
                    put("role", "system");
                    put("content", "content를 안녕하세용, 반갑습니당, 어때용? 처럼 용체로 말투 혹은 문체를 바꿔줘. 의문문에도 해당이야.");
                }},
                new HashMap<String, String>() {{
                    put("role", "user");
                    put("content", String.format("content: %s.", content));
                }}
        });

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, httpHeaders);

        String transformedContent = restTemplate.postForObject(url, entity, String.class);

        return parse(transformedContent);
    }

    private String parse(String transformedContent) {
        JSONObject jsonObj = new JSONObject(transformedContent);
        JSONArray choicesArray = jsonObj.getJSONArray("choices");

        if (choicesArray != null && choicesArray.length() > 0) {
            JSONObject firstChoice = choicesArray.getJSONObject(0);
            JSONObject message = firstChoice.getJSONObject("message");

            return message.getString("content");
        }
        return "content 문자열을 찾을 수 없습니다.";
    }

    // 테스트용
    public String transformMessage(MessageTranslatorDTO messageTranslatorDTO) {
        final String content = messageTranslatorDTO.getContent();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4");
        requestBody.put("temperature", 0.8);
        requestBody.put("max_tokens", 4096);
        requestBody.put("top_p", 1);
        requestBody.put("messages", new Object[] {
                new HashMap<String, String>() {{
                    put("content", "content를 안녕하세용, 반갑습니당, 어때용? 처럼 용체로 말투 혹은 문체를 바꿔줘. 의문문에도 해당이야.");
                }},
                new HashMap<String, String>() {{
                    put("role", "user");
                    put("content", String.format("content: %s.", content));
                }}
        });

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, httpHeaders);

        String transformedContent = restTemplate.postForObject(url, entity, String.class);

        return parse(transformedContent);
    }
}
