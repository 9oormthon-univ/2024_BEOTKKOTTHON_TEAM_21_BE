package com.teamkrews.chatRoomUser.model.response;

import com.teamkrews.User.model.UserInfo;
import com.teamkrews.message.model.request.MessageDTO;
import com.teamkrews.message.model.response.MessageResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomUserResponse {
    private Long chatRoomId;
    private Long chatRoomUserId;
    private UserInfo targetUser;
    private MessageResponse lastMessage;
}
