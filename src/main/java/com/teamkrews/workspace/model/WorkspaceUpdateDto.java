package com.teamkrews.workspace.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkspaceUpdateDto {
    private String workspaceUUID;
    private String teamName;
    private String explanation;
}
