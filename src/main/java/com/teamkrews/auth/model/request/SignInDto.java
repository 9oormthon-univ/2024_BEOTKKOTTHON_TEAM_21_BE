package com.teamkrews.auth.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {
    private String loginId;
    private String password;
}
