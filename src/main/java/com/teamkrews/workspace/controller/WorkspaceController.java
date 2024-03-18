package com.teamkrews.workspace.controller;


import com.teamkrews.User.model.User;
import com.teamkrews.auth.controller.AuthenticationPrincipal;
import com.teamkrews.userworkspace.model.UserWorkspaceCreateDto;
import com.teamkrews.userworkspace.model.UserWorkspaceJoinDto;
import com.teamkrews.userworkspace.service.UserWorkspaceService;
import com.teamkrews.utill.ApiResponse;
import com.teamkrews.workspace.model.Workspace;
import com.teamkrews.workspace.model.WorkspaceCreateDto;
import com.teamkrews.workspace.model.WorkspaceUpdateDto;
import com.teamkrews.workspace.model.request.WorkspaceCreateRequest;
import com.teamkrews.workspace.model.request.WorkspaceUpdateRequest;
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
@RequestMapping("/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {
    private final ModelMapper mapper;
    private final WorkspaceService workspaceService;
    private final UserWorkspaceService userWorkspaceService;

    @PostMapping()
    ResponseEntity<ApiResponse<WorkspaceInfoResponse>> create(@AuthenticationPrincipal User user,
                                                              @Valid @RequestBody WorkspaceCreateRequest request){
        WorkspaceCreateDto dto = mapper.map(request, WorkspaceCreateDto.class);
        Workspace workspace = workspaceService.create(dto);
        userWorkspaceService.create(new UserWorkspaceCreateDto(user, workspace));
        WorkspaceInfoResponse response = workspaceService.getWorkspaceInfoResponse(workspace.getWorkspaceUUID());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(workspace.getWorkspaceUUID())
                .toUri();

        return ResponseEntity.created(location).body(ApiResponse.success(response));
    }

    @PostMapping("/{workspaceUUID}/join")
    ResponseEntity<ApiResponse<WorkspaceInfoResponse>> join(@AuthenticationPrincipal User user,
                                                            @PathVariable String workspaceUUID){
        UserWorkspaceJoinDto joinDto = new UserWorkspaceJoinDto(user, workspaceUUID);
        WorkspaceInfoResponse infoResponse = userWorkspaceService.join(joinDto);

        return ResponseEntity.ok(ApiResponse.success(infoResponse));
    }

    @GetMapping("/{workspaceUUID}")
    ResponseEntity<ApiResponse<WorkspaceInfoResponse>> findByUUID(
            @AuthenticationPrincipal User user,
            @PathVariable String workspaceUUID
    ){
        WorkspaceInfoResponse response = workspaceService.getWorkspaceInfoResponse(workspaceUUID);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{workspaceUUID}")
    ResponseEntity<ApiResponse<WorkspaceInfoResponse>> update(
            @AuthenticationPrincipal User user,
            @PathVariable String workspaceUUID,
            @RequestBody WorkspaceUpdateRequest request
    ){
        WorkspaceUpdateDto dto = mapper.map(request, WorkspaceUpdateDto.class);
        dto.setWorkspaceUUID(workspaceUUID);

        WorkspaceInfoResponse response = workspaceService.update(dto);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
