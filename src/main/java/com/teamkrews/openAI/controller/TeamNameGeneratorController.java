package com.teamkrews.openAI.controller;

import com.teamkrews.openAI.service.TeamNameGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/openAI")
public class TeamNameGeneratorController {

    private final TeamNameGeneratorService teamNameGeneratorService;

    // 향후 url에 {workspaceId} 추가하기
    @GetMapping("/generate/teamName")
    public String generateTeamName(String seedWords) {
        return teamNameGeneratorService.generateTeamName(seedWords);
    }

    @PostMapping("/save/teamName")
    public void saveSelectedTeamName(@RequestBody String selectedTeamName) {
        teamNameGeneratorService.saveTeamName(selectedTeamName);
    }
}