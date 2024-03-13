package com.teamkrews.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //login
    WORKSPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "1404", "해당 워크스페이스를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}