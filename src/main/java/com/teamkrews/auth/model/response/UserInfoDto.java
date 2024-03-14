package com.teamkrews.auth.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private String nickName;
    private String userUUID;
    private String profileImageUrl;
}
