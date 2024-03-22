package com.teamkrews.User.controller;

import com.teamkrews.User.model.User;
import com.teamkrews.User.model.UserInfoUpdateDto;
import com.teamkrews.User.model.request.UserInfoUpdateRequest;
import com.teamkrews.User.model.response.UserInfoResponse;
import com.teamkrews.User.service.UserService;
import com.teamkrews.auth.controller.AuthenticationPrincipal;
import com.teamkrews.utill.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final ModelMapper mapper;
    private final UserService userService;
    @GetMapping()
    ResponseEntity<ApiResponse<UserInfoResponse>> findUser(@AuthenticationPrincipal User user){
        UserInfoResponse response = mapper.map(user, UserInfoResponse.class);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping()
    ResponseEntity<ApiResponse<UserInfoResponse>> updateUser(@AuthenticationPrincipal User user,
                                                             @RequestBody UserInfoUpdateRequest request){
        UserInfoUpdateDto dto = mapper.map(request, UserInfoUpdateDto.class);
        dto.setUserId(user.getId());

        UserInfoResponse response = mapper.map(userService.update(dto), UserInfoResponse.class);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
