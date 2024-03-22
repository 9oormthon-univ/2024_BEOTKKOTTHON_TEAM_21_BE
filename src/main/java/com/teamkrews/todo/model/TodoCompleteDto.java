package com.teamkrews.todo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoCompleteDto {
    private Long userId;
    private Long todoId;

    public TodoCompleteDto(Long userId, Long todoId) {
        this.userId = userId;
        this.todoId = todoId;
    }
}
