package com.teamkrews.todo.model.response;

import com.teamkrews.User.model.response.UserInfoResponse;
import com.teamkrews.workspace.model.Workspace;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoInfoResponse {
    private Long id;
    private String content;
    private Boolean completed;
    private UserInfoResponse userInfo;
    private String workspaceUUID;
}
