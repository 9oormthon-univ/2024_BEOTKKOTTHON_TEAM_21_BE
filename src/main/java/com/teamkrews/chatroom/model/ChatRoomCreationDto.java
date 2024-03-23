package com.teamkrews.chatroom.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRoomCreationDto {
        private String workspaceUUID;
        private Long creatorUserId;
        private List<Long> userIds;
}
