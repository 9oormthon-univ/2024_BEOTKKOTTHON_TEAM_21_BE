package com.teamkrews.chat.controller;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.repository.ChatRoomRepository;
import com.teamkrews.chat.service.ChatRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@Slf4j
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;

    // 1:1 채팅방 생성
    @PostMapping("chat/create/chatRoom/{userId1}/{userId2}")
    public ResponseEntity<ChatRoom> createChatRoom(@PathVariable Long userId1, @PathVariable Long userId2) {
        try {
            ChatRoom chatRoom = chatRoomService.createChatRoomWithUser(userId1, userId2);
            return ResponseEntity.ok(chatRoom);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 채팅방 목록 조회
    // 나중에 내가 먼저 보낸 채팅방 & 받은 채팅방으로 분리하기
    @GetMapping("/chat/list/chatRooms")
    public ResponseEntity<List<ChatRoom>> getAllChatRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        return ResponseEntity.ok(chatRooms);
    }
}