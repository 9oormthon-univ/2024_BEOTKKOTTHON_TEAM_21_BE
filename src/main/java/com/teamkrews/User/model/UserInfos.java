package com.teamkrews.User.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfos {
    private List<UserInfo> userInfoList;

    public UserInfos(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }
}
