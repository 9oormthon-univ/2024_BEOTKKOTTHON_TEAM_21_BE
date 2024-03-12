package com.teamkrews.auth.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDto {
    private String userUUID;
    private String authToken;

    public AuthDto(String userUUID, String authToken) {
        this.userUUID = userUUID;
        this.authToken = authToken;
    }
}
