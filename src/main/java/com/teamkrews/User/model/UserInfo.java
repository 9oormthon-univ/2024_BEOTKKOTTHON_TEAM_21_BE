package com.teamkrews.User.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String nickName;
    private String profileImageUrl;
    private final String ANONYMOUS_PROFILE_IMAGE_URL =
            "https://teamkrewsbucket.s3.ap-northeast-2.amazonaws.com/TeamKewsAnonymousProfileImage.png";
    public static UserInfo getAnonymousUserInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName("익명");
        userInfo.setProfileImageUrl("ANONYMOUS_PROFILE_IMAGE_URL");
        return userInfo;
    }
}
