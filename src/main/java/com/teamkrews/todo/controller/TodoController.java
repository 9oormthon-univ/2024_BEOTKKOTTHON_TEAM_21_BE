package com.teamkrews.todo.controller;


import com.teamkrews.User.model.User;
import com.teamkrews.auth.controller.AuthenticationPrincipal;
import com.teamkrews.todo.model.Todo;
import com.teamkrews.todo.model.TodoCreateDto;
import com.teamkrews.todo.model.request.TodoCreateRequest;
import com.teamkrews.todo.model.response.TodoInfoResponse;
import com.teamkrews.todo.service.TodoService;
import com.teamkrews.utill.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    private final ModelMapper mapper;

    @PostMapping()
    ResponseEntity<ApiResponse<TodoInfoResponse>> create(@AuthenticationPrincipal User user,
                                                         @RequestBody TodoCreateRequest request){
        TodoCreateDto createDto = mapper.map(request, TodoCreateDto.class);
        createDto.setUserId(user.getId());
        Todo savedTodo = todoService.create(createDto);

        TodoInfoResponse response = todoService.convertToResponse(savedTodo);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
