package com.teamkrews.chatRoomUser.model.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomUserResponses {
    private List<ChatRoomUserResponse> chatRooms = new ArrayList<>();
}
