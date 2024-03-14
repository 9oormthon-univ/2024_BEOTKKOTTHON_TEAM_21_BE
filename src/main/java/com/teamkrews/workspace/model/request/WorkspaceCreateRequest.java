package com.teamkrews.workspace.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkspaceCreateRequest {
    @NotNull
    private String teamName;

    @NotNull
    private String profileImageUrl;


}