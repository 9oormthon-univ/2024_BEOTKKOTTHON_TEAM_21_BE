package com.teamkrews.userworkspace.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserWorkspaceInfoResponses {
    private Long userId;
    private String nickName;
    private List<UserWorkspaceInfo> workspaceInfoList;

    public UserWorkspaceInfoResponses(Long userId, String nickName, List<UserWorkspaceInfo> workspaceInfoList) {
        this.userId = userId;
        this.nickName = nickName;
        this.workspaceInfoList = workspaceInfoList;
    }
}
