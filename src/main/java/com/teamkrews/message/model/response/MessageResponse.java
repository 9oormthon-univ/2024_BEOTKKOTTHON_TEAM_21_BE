package com.teamkrews.message.model.response;

import com.teamkrews.User.model.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
    private Long senderId;
    private String content;
    private String dateTime;
    private UserInfo userInfo;
}
