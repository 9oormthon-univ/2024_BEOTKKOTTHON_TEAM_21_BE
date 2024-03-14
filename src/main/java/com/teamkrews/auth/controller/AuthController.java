package com.teamkrews.auth.controller;


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

    @GetMapping("/healthCheck")
    public String healthCheck(){
        return "Connect !";
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
