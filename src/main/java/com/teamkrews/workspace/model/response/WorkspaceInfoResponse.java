package com.teamkrews.workspace.model.response;

import com.teamkrews.User.model.response.UserInfoResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkspaceInfoResponse {
    private String workspaceUUID;
    private String teamName;
    private List<UserInfoResponse> userInfoResponseList;
}
