package com.teamkrews.chatroom.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRoomCreationRequest {
    private String workspaceUUID;
    private List<Long> userIds;
}
