package com.teamkrews.userworkspace.model;

import com.teamkrews.User.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWorkspaceDeleteDto {
    private User user;
    private String workspaceUUID;

    public UserWorkspaceDeleteDto(User user, String workspaceUUID) {
        this.user = user;
        this.workspaceUUID = workspaceUUID;
    }
}
