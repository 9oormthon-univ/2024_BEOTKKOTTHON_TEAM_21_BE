package com.teamkrews.todo.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoCreateRequest {
    @NotNull
    private String content;
    private String workspaceUUID;
}
