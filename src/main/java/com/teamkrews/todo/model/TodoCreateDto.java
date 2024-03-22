package com.teamkrews.todo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoCreateDto {
    private String content;
    private String workspaceUUID;
    private Long userId;
}
