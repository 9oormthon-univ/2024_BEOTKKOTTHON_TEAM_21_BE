package com.teamkrews.chat.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDetailResponse {
    private Long chatRoomId;
    private Long chatRoomUserId;
    private UserInfoResponse targetUser;
}