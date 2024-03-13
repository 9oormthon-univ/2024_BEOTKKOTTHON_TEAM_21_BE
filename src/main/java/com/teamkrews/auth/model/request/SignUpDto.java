package com.teamkrews.auth.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {

    @Email
    private String email;

    @NotNull
    private String loginId;

    @Min(8)
    private String password;
}
