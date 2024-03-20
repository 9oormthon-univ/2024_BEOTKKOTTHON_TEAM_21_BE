package com.teamkrews.openAI.controller;

import com.teamkrews.openAI.model.request.SeedWords;
import com.teamkrews.openAI.model.response.SavedTeamNameResponse;
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
    public ResponseEntity<ApiResponse<TeamNames>> generateTeamName(@RequestBody SeedWords request) {
        List<String> teamNamesList = teamNameGeneratorService.generateTeamName(request);

        TeamNames teamNames = new TeamNames();
        teamNames.setTeamNames(teamNamesList);

        return ResponseEntity.ok(ApiResponse.success(teamNames));
    }

    @PostMapping("/save/teamName/workspace/{workspaceUUID}")
    public ResponseEntity<ApiResponse<SavedTeamNameResponse>> saveSelectedTeamName(@PathVariable String workspaceUUID, @RequestBody String selectedTeamName) {
        Workspace workspace = teamNameGeneratorService.saveTeamName(workspaceUUID, selectedTeamName);

        SavedTeamNameResponse response = new SavedTeamNameResponse();
        response.setTeamName(selectedTeamName);
        response.setWorkspaceUUID(workspaceUUID);
        response.setExplanation(workspace.getExplanation());

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}