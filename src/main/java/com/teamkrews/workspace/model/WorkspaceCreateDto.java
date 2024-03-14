package com.teamkrews.workspace.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkspaceCreateDto {
    @NotNull
    private String teamName;

    @NotNull
    private String profileImageUrl;

}
