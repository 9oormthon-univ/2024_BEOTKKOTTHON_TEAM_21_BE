package com.teamkrews.workspace.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkspaceUpdateRequest {
    private String teamName;
    private String explanation;
}