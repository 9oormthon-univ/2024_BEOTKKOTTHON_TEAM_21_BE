package com.teamkrews.dm.repository;

import com.teamkrews.dm.model.ChatRoom;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


// 채팅방 생성, 정보 조회
@Repository
@Slf4j
public class ChatRoomRepository {

    private Map<Long, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    // 모든 채팅방 조회
    public List<ChatRoom> findAllRoom() {
        // 채팅방 생성 순서 최근 순으로 반환
        List<ChatRoom> chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    // 채팅방 생성
    public ChatRoom createChatRoom() {
        ChatRoom chatRoom = ChatRoom.create();
        chatRoomMap.put(chatRoom.getChatRoomId(), chatRoom);
        return chatRoom;
    }
}