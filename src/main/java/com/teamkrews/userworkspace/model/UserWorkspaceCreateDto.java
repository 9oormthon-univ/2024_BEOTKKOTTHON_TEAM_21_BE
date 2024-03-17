package com.teamkrews.userworkspace.model;


import com.teamkrews.User.model.User;
import com.teamkrews.workspace.model.Workspace;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWorkspaceCreateDto {
    private User user;
    private Workspace workspace;
    private String explanation;

    public UserWorkspaceCreateDto(User user, Workspace workspace) {
        this.user = user;
        this.workspace = workspace;
    }
}
