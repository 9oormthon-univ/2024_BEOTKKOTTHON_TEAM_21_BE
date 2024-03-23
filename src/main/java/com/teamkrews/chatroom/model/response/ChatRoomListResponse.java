package com.teamkrews.chatroom.model.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomListResponse {
    private List<ChatRoomDetailResponse> chatRooms = new ArrayList<>();
}
