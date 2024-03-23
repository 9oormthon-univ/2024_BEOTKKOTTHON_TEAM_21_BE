package com.teamkrews.chatroom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomNewStateTrueDto {
    public  Long chatRoomId;
    public  final Boolean newState = Boolean.TRUE;

    public ChatRoomNewStateTrueDto(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
