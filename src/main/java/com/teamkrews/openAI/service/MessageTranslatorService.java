package com.teamkrews.openAI.service;

import com.teamkrews.chat.model.Message;
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
    public String transformMessage(Message message) {

        final String content = message.getContent();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("temperature", 0.8);
        requestBody.put("max_tokens", 4096);
        requestBody.put("top_p", 1);
        requestBody.put("messages", new Object[] {
                new HashMap<String, String>() {{
                    put("role", "system");
                    put("content", "content를 '~용' 으로 말투를 바꿔줘. 의문문에도 해당이야.");
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
        return "No content found";
    }

    // 테스트용
    public String transformMessage(MessageTranslatorDTO messageTranslatorDTO) {
        final String content = messageTranslatorDTO.getContent();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("temperature", 0.8);
        requestBody.put("max_tokens", 4096);
        requestBody.put("top_p", 1);
        requestBody.put("messages", new Object[] {
                new HashMap<String, String>() {{
                    put("role", "system");
                    put("content", "content를 '~용' 으로 말투를 바꿔줘.");
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
