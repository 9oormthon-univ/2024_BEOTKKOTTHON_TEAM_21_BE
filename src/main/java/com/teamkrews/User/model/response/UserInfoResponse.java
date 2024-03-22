package com.teamkrews.User.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {
    private String id;
    private String nickName;
    private String userUUID;
    private String email;
    private String profileImageUrl;
}