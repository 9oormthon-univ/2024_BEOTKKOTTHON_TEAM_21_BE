package com.teamkrews.chat.controller;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.request.ChatRoomCreationRequest;
import com.teamkrews.chat.model.response.ChatRoomResponse;
import com.teamkrews.chat.service.ChatRoomService;
import com.teamkrews.utill.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/chatRoom")
@Slf4j
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 1:1 채팅방 생성
    // @RequestBody는 하나의 메서드에 여러 개일 수 없음!
    // 따라서, 두 개의 Long 타입 파라미터을 받으려면 하나의 DTO 안에 넣어줘야함!
    @PostMapping("/create/chatRoom")
    public ResponseEntity<ApiResponse<ChatRoomResponse>> createChatRoom(@RequestBody ChatRoomCreationRequest request) {
        try {
            ChatRoom chatRoom = chatRoomService.createChatRoomWithUser(request);

            ChatRoomResponse chatRoomResponse = new ChatRoomResponse();
            chatRoomResponse.setChatRoomId(chatRoom.getChatRoomId());
            chatRoomResponse.setWorkspaceId(chatRoom.getWorkspace().getId());

            return ResponseEntity.ok(ApiResponse.success(chatRoomResponse));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 채팅방 목록 조회
    // 나중에 내가 먼저 보낸 채팅방 & 받은 채팅방으로 분리하기

}