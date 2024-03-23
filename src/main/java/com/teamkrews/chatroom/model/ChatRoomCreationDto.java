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
        private Boolean isGroup = Boolean.FALSE;

        public ChatRoomCreationDto(){
        }
        public ChatRoomCreationDto(String workspaceUUID, Long creatorUserId, List<Long> userIds, Boolean isGroup) {
                this.workspaceUUID = workspaceUUID;
                this.creatorUserId = creatorUserId;
                this.userIds = userIds;
                this.isGroup = isGroup;
        }
}
