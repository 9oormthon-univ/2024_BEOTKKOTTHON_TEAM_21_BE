package com.teamkrews.openAI.service;

import com.teamkrews.openAI.model.request.SeedWords;
import com.teamkrews.openAI.model.response.TeamNames;
import com.teamkrews.utill.ApiResponse;
import com.teamkrews.workspace.model.Workspace;
import com.teamkrews.workspace.repository.WorkspaceRepository;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("temperature", 0.8);
        requestBody.put("max_tokens", 64);
        requestBody.put("top_p", 1);
        requestBody.put("messages", new Object[] {
                new HashMap<String, String>() {{
                    put("role", "system");
                    put("content", "시드 단어들을 기반으로 한글 팀 이름을 4개 생성해줘.");
                }},
                new HashMap<String, String>() {{
                    put("role", "user");
                    put("content", String.format("시드 단어: %s.", seedWordsStr));
                }}
        });

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, httpHeaders);

        String generatedTeamNames = restTemplate.postForObject(url, entity, String.class);

        List<String> teamNames = parse(generatedTeamNames);

        return teamNames;
    }

    private List<String> parse(String generatedTeamNames) {
        return Arrays.asList(generatedTeamNames.split(","));
    }

    // 선택한 팀명 저장
    public Workspace saveTeamName(String workspaceUUID, String selectedTeamName) {
        Workspace workspace = workspaceRepository.findById(workspaceUUID)
                .orElseThrow(() -> new IllegalArgumentException("Workspace with uuid " + workspaceUUID + " not found"));
        workspace.setTeamName(selectedTeamName);
        workspaceRepository.save(workspace);

        return workspace;
    }
}
