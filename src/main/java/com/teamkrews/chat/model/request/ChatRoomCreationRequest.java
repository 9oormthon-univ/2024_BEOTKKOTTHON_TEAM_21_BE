package com.teamkrews.chat.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreationRequest {
    private Long workspaceId;
    private Long currentUserId;
    private Long targetUserId;
}
