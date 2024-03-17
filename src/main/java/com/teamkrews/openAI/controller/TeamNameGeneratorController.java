package com.teamkrews.openAI.controller;

import com.teamkrews.openAI.model.request.SeedWords;
import com.teamkrews.openAI.model.response.TeamNames;
import com.teamkrews.openAI.service.TeamNameGeneratorService;
import com.teamkrews.utill.ApiResponse;
import com.teamkrews.workspace.model.Workspace;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/openAI")
public class TeamNameGeneratorController {

    private final TeamNameGeneratorService teamNameGeneratorService;

    @PostMapping("/generate/teamNames")
    public ResponseEntity<TeamNames> generateTeamName(@RequestBody SeedWords request) {
        TeamNames response = new TeamNames();

        List<String> teamNames = teamNameGeneratorService.generateTeamName(request);
        response.setTeamNames(teamNames);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/save/teamName/workspace/{workspaceUUID}")
    public ResponseEntity<ApiResponse<Workspace>> saveSelectedTeamName(@PathVariable String workspaceUUID, @RequestBody String selectedTeamName) {
        Workspace workspace = teamNameGeneratorService.saveTeamName(workspaceUUID, selectedTeamName);

        return ResponseEntity.ok(ApiResponse.success(workspace));
    }
}