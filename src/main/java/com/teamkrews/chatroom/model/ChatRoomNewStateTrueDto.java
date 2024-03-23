package com.teamkrews.chatroom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomNewStateTrueDto {
    public  Long chatRoomId;
    public  Long senderId;
    public  final Boolean newState = Boolean.TRUE;

    public ChatRoomNewStateTrueDto(Long chatRoomId, Long senderId) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
    }
}
