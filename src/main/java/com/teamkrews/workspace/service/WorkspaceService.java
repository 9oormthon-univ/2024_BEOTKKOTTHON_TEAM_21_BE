package com.teamkrews.workspace.service;


import com.teamkrews.User.model.User;
import com.teamkrews.User.model.response.UserInfoResponse;
import com.teamkrews.global.exception.CustomException;
import com.teamkrews.global.exception.ErrorCode;
import com.teamkrews.userworkspace.model.UserWorkspace;
import com.teamkrews.userworkspace.repository.UserWorkspaceRepository;
import com.teamkrews.workspace.model.Workspace;
import com.teamkrews.workspace.model.WorkspaceCreateDto;
import com.teamkrews.workspace.model.WorkspaceUpdateDto;
import com.teamkrews.workspace.model.response.WorkspaceInfoResponse;
import com.teamkrews.workspace.repository.WorkspaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final ModelMapper mapper;
    private final WorkspaceRepository workspaceRepository;
    private final UserWorkspaceRepository userWorkspaceRepository;

    @Transactional
    public Workspace create(final WorkspaceCreateDto dto){
        Workspace workspace = mapper.map(dto, Workspace.class);
        workspace.setWorkspaceUUID(UUID.randomUUID().toString());
        Workspace saved = workspaceRepository.save(workspace);

        return saved;
    }

    public Workspace findByUUID(final String workspaceUUID){
        Optional<Workspace> workspaceOptional = workspaceRepository.findByWorkspaceUUID(workspaceUUID);

        if (workspaceOptional.isEmpty()){
            throw new CustomException(ErrorCode.WORKSPACE_NOT_FOUND);
        }

        return workspaceOptional.get();
    }


    public WorkspaceInfoResponse getWorkspaceInfoResponse(final String workspaceUUID){
        Workspace workspace = findByUUID(workspaceUUID);
        List<UserInfoResponse> userInfoList = getUserInfoResponses(workspace);

        WorkspaceInfoResponse infoResponse = mapper.map(workspace, WorkspaceInfoResponse.class);
        infoResponse.setUserInfoResponseList(userInfoList);

        return infoResponse;
    }

    private List<UserInfoResponse> getUserInfoResponses(Workspace workspace) {
        List<UserWorkspace> userWorkspaceList = userWorkspaceRepository.findAllByWorkspace(workspace);

        List<User> userList = userWorkspaceList.stream().map(
                (e) -> e.getUser()
        ).collect(Collectors.toList());

        List<UserInfoResponse> userInfoList = userList.stream().map(
                (e) -> mapper.map(e, UserInfoResponse.class)
        ).collect(Collectors.toList());
        return userInfoList;
    }

    @Transactional
    public WorkspaceInfoResponse update(final WorkspaceUpdateDto dto){
        Workspace workspace = findByUUID(dto.getWorkspaceUUID());

        workspace.setTeamName(dto.getTeamName());
        workspace.setExplanation(dto.getExplanation());

        WorkspaceInfoResponse infoResponse = mapper.map(workspace, WorkspaceInfoResponse.class);
        List<UserInfoResponse> userInfoList = getUserInfoResponses(workspace);
        infoResponse.setUserInfoResponseList(userInfoList);

        return infoResponse;
    }
}
