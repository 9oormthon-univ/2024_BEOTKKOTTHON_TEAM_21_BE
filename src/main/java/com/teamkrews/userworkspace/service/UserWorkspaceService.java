package com.teamkrews.userworkspace.service;

import com.teamkrews.User.model.User;
import com.teamkrews.User.model.response.UserInfoResponse;
import com.teamkrews.userworkspace.model.UserWorkspace;

import com.teamkrews.userworkspace.model.UserWorkspaceCreateDto;
import com.teamkrews.userworkspace.model.UserWorkspaceJoinDto;
import com.teamkrews.userworkspace.repository.UserWorkspaceRepository;
import com.teamkrews.workspace.model.Workspace;
import com.teamkrews.workspace.service.WorkspaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserWorkspaceService {
    private final ModelMapper mapper;
    private final UserWorkspaceRepository userWorkspaceRepository;
    private final WorkspaceService workspaceService;

    @Transactional
    public UserWorkspace create(UserWorkspaceCreateDto dto){
        Optional<UserWorkspace> userWorkspaceOptional = userWorkspaceRepository.findByUserAndWorkspace(dto.getUser(), dto.getWorkspace());

        if(!userWorkspaceOptional.isEmpty())
            return userWorkspaceOptional.get();

        UserWorkspace userWorkspace = mapper.map(dto, UserWorkspace.class);
        UserWorkspace saved = userWorkspaceRepository.save(userWorkspace);
        return saved;
    }

    @Transactional
    public UserWorkspace join(UserWorkspaceJoinDto dto){
        Workspace workspace = workspaceService.findByUUID(dto.getWorkspaceUUID());

        UserWorkspaceCreateDto userWorkspaceCreateDto = new UserWorkspaceCreateDto(dto.getUser(), workspace);
        return create(userWorkspaceCreateDto);
    }
}
