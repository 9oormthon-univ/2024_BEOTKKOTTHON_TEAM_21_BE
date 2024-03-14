package com.teamkrews.userworkspace.model;

import com.teamkrews.User.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWorkspaceJoinDto {
    private User user;
    private String workspaceUUID;

    public UserWorkspaceJoinDto(User user, String workspaceUUID) {
        this.user = user;
        this.workspaceUUID = workspaceUUID;
    }
}
