package com.teamkrews.chat.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreationRequest {
    private String workspaceUUID;
    private Long currentUserId;
    private Long targetUserId;
}
