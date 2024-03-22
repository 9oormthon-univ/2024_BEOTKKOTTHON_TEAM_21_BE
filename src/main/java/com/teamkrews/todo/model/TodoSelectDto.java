package com.teamkrews.todo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoSelectDto {
    private String workspaceUUID;
    private Boolean completed;

    public TodoSelectDto(String workspaceUUID, Boolean completed) {
        this.workspaceUUID = workspaceUUID;
        this.completed = completed;
    }
}
