package com.teamkrews.chat.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomNewStateFalseDto {
    public  Long chatRoomUserId;
    public  final Boolean newState = Boolean.FALSE;

    public ChatRoomNewStateFalseDto(Long chatRoomUserId) {
        this.chatRoomUserId = chatRoomUserId;
    }
}
