package com.teamkrews.User.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoUpdateRequest {
    private String nickName;
    private String profileImageUrl;
}
