package com.teamkrews.User.controller;

import com.teamkrews.User.model.User;
import com.teamkrews.User.model.response.UserInfoResponse;
import com.teamkrews.auth.controller.AuthenticationPrincipal;
import com.teamkrews.utill.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final ModelMapper mapper;
    @GetMapping()
    ResponseEntity<ApiResponse<UserInfoResponse>> findUser(@AuthenticationPrincipal User user){
        UserInfoResponse response = mapper.map(user, UserInfoResponse.class);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
