package com.teamkrews.todo.service;

import com.teamkrews.User.model.User;
import com.teamkrews.User.model.response.UserInfoResponse;
import com.teamkrews.User.service.UserService;
import com.teamkrews.global.exception.CustomException;
import com.teamkrews.global.exception.ErrorCode;
import com.teamkrews.todo.model.Todo;
import com.teamkrews.todo.model.TodoCompleteDto;
import com.teamkrews.todo.model.TodoCreateDto;
import com.teamkrews.todo.model.response.TodoInfoResponse;
import com.teamkrews.todo.repository.TodoRepository;
import com.teamkrews.workspace.model.Workspace;
import com.teamkrews.workspace.service.WorkspaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final UserService userService;
    private final WorkspaceService workspaceService;
    private final TodoRepository todoRepository;
    private final ModelMapper mapper;

    @Transactional
    public Todo create(TodoCreateDto dto){
        Todo todo = mapper.map(dto, Todo.class);
        User user = userService.getById(dto.getUserId());
        Workspace workspace = workspaceService.findByUUID(dto.getWorkspaceUUID());


        todo.setUser(user);
        todo.setWorkspace(workspace);
        return todoRepository.save(todo);
    }

    public TodoInfoResponse convertToResponse(Todo todo){
        TodoInfoResponse todoInfoResponse = mapper.map(todo, TodoInfoResponse.class);
        UserInfoResponse userInfoResponse = mapper.map(todo.getUser(), UserInfoResponse.class);

        todoInfoResponse.setUserInfo(userInfoResponse);
        todoInfoResponse.setWorkspaceUUID(todo.getWorkspace().getWorkspaceUUID());

        return todoInfoResponse;
    }

    @Transactional
    public Todo completeTodo(TodoCompleteDto dto){
        User user = userService.getById(dto.getUserId());
        Todo todo = findById(dto.getTodoId());

        if (todo.getUser().getId() != user.getId()){
            throw new CustomException(ErrorCode.TODO_COMPLETE_UNAUTHORIZED);
        }

        todo.setCompleted(Boolean.TRUE);
        return todo;
    }

    public Todo findById(Long todoId){
        Optional<Todo> todoOptional = todoRepository.findById(todoId);

        if(todoOptional.isEmpty())
            throw new CustomException(ErrorCode.TODO_NOT_FOUND);

        return todoOptional.get();
    }
}
