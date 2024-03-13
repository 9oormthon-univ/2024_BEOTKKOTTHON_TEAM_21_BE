package com.teamkrews.workspace.controller;


import com.teamkrews.User.model.User;
import com.teamkrews.auth.controller.AuthenticationPrincipal;
import com.teamkrews.userworkspace.model.UserWorkspace;
import com.teamkrews.userworkspace.model.UserWorkspaceCreateDto;
import com.teamkrews.userworkspace.model.UserWorkspaceJoinDto;
import com.teamkrews.userworkspace.service.UserWorkspaceService;
import com.teamkrews.utill.ApiResponse;
import com.teamkrews.workspace.model.Workspace;
import com.teamkrews.workspace.model.request.WorkspaceCreateRequest;
import com.teamkrews.workspace.model.response.WorkspaceInfoResponse;
import com.teamkrews.workspace.service.WorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {
    private final ModelMapper mapper;
    private final WorkspaceService workspaceService;
    private final UserWorkspaceService userWorkspaceService;

    @PostMapping()
    ResponseEntity<ApiResponse<WorkspaceInfoResponse>> create(@AuthenticationPrincipal User user,
                                                              @Valid @RequestBody WorkspaceCreateRequest request){
        Workspace workspace = workspaceService.create(request.getTeamName());
        WorkspaceInfoResponse response = mapper.map(workspace, WorkspaceInfoResponse.class);
        userWorkspaceService.create(new UserWorkspaceCreateDto(user, workspace));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(workspace.getWorkspaceUUID())
                .toUri();

        return ResponseEntity.created(location).body(ApiResponse.success(response));
    }

    @PostMapping("/{workspaceUUID}/join")
    ResponseEntity<ApiResponse<Void>> join(@AuthenticationPrincipal User user,
                                                            @PathVariable String workspaceUUID){
        UserWorkspaceJoinDto joinDto = new UserWorkspaceJoinDto(user, workspaceUUID);
        UserWorkspace join = userWorkspaceService.join(joinDto);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/{workspaceUUID}")
    ResponseEntity<ApiResponse<WorkspaceInfoResponse>> findByUUID(
            @AuthenticationPrincipal User user,
            @PathVariable String workspaceUUID
    ){
        Workspace workspace = workspaceService.findByUUID(workspaceUUID);
        //workspace 에서의 userWorkspace List를 통해 찾아야함
        return ResponseEntity.ok(ApiResponse.success(null));
    }



}
