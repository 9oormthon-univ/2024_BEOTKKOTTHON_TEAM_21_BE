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

    @GetMapping("/generate/teamName")
    public String generateTeamName(@RequestParam Long workspaceId, String seedWords) {
        return teamNameGeneratorService.generateTeamName(seedWords);
    }

    @PostMapping("/save/workspace/{workspaceId}/teamName")
    public void saveSelectedTeamName(@RequestParam Long workspaceId, @RequestBody String selectedTeamName) {
        teamNameGeneratorService.saveTeamName(workspaceId, selectedTeamName);
    }
}