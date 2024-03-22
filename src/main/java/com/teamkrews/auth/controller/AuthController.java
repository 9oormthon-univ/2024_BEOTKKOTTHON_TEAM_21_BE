package com.teamkrews.auth.controller;


import com.teamkrews.User.service.UserService;
import com.teamkrews.auth.model.request.CheckLoginIdDto;
import com.teamkrews.auth.model.request.SignInDto;
import com.teamkrews.auth.model.request.SignUpDto;
import com.teamkrews.auth.model.response.AuthDto;
import com.teamkrews.auth.model.response.UserInfoDto;
import com.teamkrews.auth.service.AuthService;
import com.teamkrews.utill.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/healthCheck")
    public String healthCheck(){
        return "Connect !";
    }

    @PostMapping("/check-duplicate")
    public ResponseEntity<ApiResponse<Boolean>>  checkLoginId(@RequestBody CheckLoginIdDto checkLoginIdDto){
        Boolean duplicatedLoginId = userService.isDuplicatedLoginId(checkLoginIdDto.getLoginId());
        return ResponseEntity.ok(ApiResponse.success(duplicatedLoginId));
    }

    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<UserInfoDto>> signUp(
            @Valid @RequestBody SignUpDto signUpDto
    ) {
        UserInfoDto userInfoDto = authService.signUp(signUpDto);
        return ResponseEntity.ok(ApiResponse.success(userInfoDto));
    }

    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse<AuthDto>> signIn(
            @Valid @RequestBody SignInDto signInDto
    ) {
        AuthDto authDto = authService.signIn(signInDto);
        return ResponseEntity.ok(ApiResponse.success(authDto));
    }
}
