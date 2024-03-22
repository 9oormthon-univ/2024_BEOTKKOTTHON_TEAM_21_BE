package com.teamkrews.todo.model.response;

import com.teamkrews.User.model.response.UserInfoResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodoInfoResponses {
    List<TodoInfoResponse> todoInfoResponseList;

    public TodoInfoResponses(List<TodoInfoResponse> todoInfoResponseList) {
        this.todoInfoResponseList = todoInfoResponseList;
    }
}
