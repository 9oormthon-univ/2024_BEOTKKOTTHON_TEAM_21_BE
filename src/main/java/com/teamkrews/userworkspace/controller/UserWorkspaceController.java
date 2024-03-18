package com.teamkrews.userworkspace.controller;


import com.teamkrews.User.model.User;
import com.teamkrews.auth.controller.AuthenticationPrincipal;
import com.teamkrews.userworkspace.model.UserWorkspaceDeleteDto;
import com.teamkrews.userworkspace.model.response.UserWorkspaceInfoResponses;
import com.teamkrews.userworkspace.service.UserWorkspaceService;
import com.teamkrews.utill.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userworkspaces")
@RequiredArgsConstructor
public class UserWorkspaceController{
    private final ModelMapper mapper;
    private final UserWorkspaceService userWorkspaceService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<UserWorkspaceInfoResponses>> getAll(
            @AuthenticationPrincipal User user
    ){
        UserWorkspaceInfoResponses responses = userWorkspaceService.findAllByUser(user);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @DeleteMapping("/{workspaceUUID}")
    public ResponseEntity<ApiResponse<UserWorkspaceInfoResponses>> delete(
            @AuthenticationPrincipal User user,
            @PathVariable final String workspaceUUID
    ){
        UserWorkspaceInfoResponses responses = userWorkspaceService.deleteUserWorkspace(new UserWorkspaceDeleteDto(user, workspaceUUID));
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}
