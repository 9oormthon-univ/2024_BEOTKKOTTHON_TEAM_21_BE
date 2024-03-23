package com.teamkrews.chatRoomUser.model.response;

import com.teamkrews.User.model.UserInfos;
import com.teamkrews.message.model.response.MessageResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomUserResponse {
    private Long chatRoomId;
    private Long chatRoomUserId;
    private UserInfos targetUsers;
    private MessageResponse lastMessage;
}
