package com.teamkrews.chat.controller;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@Slf4j
public class ChatRoomController {

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
}