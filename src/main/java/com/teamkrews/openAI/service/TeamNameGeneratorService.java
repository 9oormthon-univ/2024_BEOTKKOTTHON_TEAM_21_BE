package com.teamkrews.openAI.service;

import com.teamkrews.openAI.model.request.SeedWords;
import com.teamkrews.workspace.model.Workspace;
import com.teamkrews.workspace.repository.WorkspaceRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class TeamNameGeneratorService {

    @Value("${openai.url.prompt}")
    private String url;

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    private final WorkspaceRepository workspaceRepository;

    // 팀명 4개 생성
    public List<String> generateTeamName(SeedWords request) {

        final List<String> seedWords = request.getSeedWords();

        String seedWordsStr = String.join(", ", seedWords);

        /*
        {
            "teamNames": "{\n  \"id\": \"",\n  \"object\": \"chat.completion\",\n  \"created\": 1710689899,\n  \"model\": \"gpt-3.5-turbo-0125\",\n  \"choices\": [\n    {\n      \"index\": 0,\n      \"message\": {\n        \"role\": \"assistant\",\n        \"content\": \"1. 개발귀요미팀\\n2. 카카오ESG개발팀\\n3. 귀여운개발자들\\n4. ESG귀여운개발팀\"\n      },\n      \"logprobs\": null,\n      \"finish_reason\": \"stop\"\n    }\n  ],\n  \"usage\": {\n    \"prompt_tokens\": 72,\n    \"completion_tokens\": 53,\n    \"total_tokens\": 125\n  },\n  \"system_fingerprint\": \"fp_4f2ebda25a\"\n}\n"
        }
         */
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("temperature", 0.8);
        requestBody.put("max_tokens", 60);
        requestBody.put("top_p", 1);
        requestBody.put("messages", new Object[] {
                new HashMap<String, String>() {{
                    put("role", "system");
                    put("content", "우리 팀을 나타내는 키워드를 바탕으로 한글 팀 이름을 4개 추천해줘.");
                }},
                new HashMap<String, String>() {{
                    put("role", "user");
                    put("content", String.format("키워드: %s.", seedWordsStr));
                }}
        });

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, httpHeaders);

        String generatedTeamNames = restTemplate.postForObject(url, entity, String.class);

        return parse(generatedTeamNames);
    }

    /*
    {
        "success": true,
        "code": "200",
        "message": "Success",
        "data": {
            "teamNames": [
                "1. 개발귀요미팀",
                "2. 카카오ESG개발팀",
                "3. 귀여운카카오ESG팀",
                "4. 카카오ESG개발단체"
            ]
        }
    }
     */
    private List<String> parse(String generatedTeamNames) {
        List<String> teamNamesList = new ArrayList<>();

        int contentStartIndex = generatedTeamNames.indexOf("\"content\": \"");
        if (contentStartIndex != -1) {
            contentStartIndex += "\"content\": \"".length();

            int contentEndIndex = generatedTeamNames.indexOf("\"", contentStartIndex);
            if (contentEndIndex != -1) {
                String contentValue = generatedTeamNames.substring(contentStartIndex, contentEndIndex);
                String[] teamNamesArray = contentValue.split("\\\\n");

                for (String teamName : teamNamesArray) {
                    teamNamesList.add(teamName.trim()); // 공백 제거
                }
            } else {
                System.out.println("content 문자열의 마지막 따옴표를 찾을 수 없습니다.");
            }
        } else {
            System.out.println("content 문자열을 찾을 수 없습니다.");
        }

        return teamNamesList;
    }

    // 선택한 팀명 저장
    public Workspace saveTeamName(String workspaceUUID, String selectedTeamName) {
        Workspace workspace = workspaceRepository.findByWorkspaceUUID(workspaceUUID)
                .orElseThrow(() -> new IllegalArgumentException("Workspace with uuid " + workspaceUUID + " not found"));
        workspace.setTeamName(selectedTeamName);
        workspaceRepository.save(workspace);

        return workspace;
    }
}
