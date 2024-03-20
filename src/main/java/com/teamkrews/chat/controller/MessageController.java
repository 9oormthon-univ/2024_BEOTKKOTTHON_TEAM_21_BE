package com.teamkrews.chat.controller;

import com.teamkrews.User.model.User;
import com.teamkrews.auth.controller.AuthenticationPrincipal;
import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.ChatRoomMessage;
import com.teamkrews.chat.model.ChatRoomUser;
import com.teamkrews.chat.model.Message;
import com.teamkrews.chat.repository.ChatRoomMessageRepository;
import com.teamkrews.chat.repository.ChatRoomRepository;
import com.teamkrews.chat.repository.ChatRoomUserRepository;
import com.teamkrews.chat.service.MessageService;
import com.teamkrews.workspace.model.Workspace;
import com.teamkrews.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@Controller
@Slf4j
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final WorkspaceRepository workspaceRepository;
    private final ChatRoomMessageRepository chatRoomMessageRepository;


    // 특정 채팅방의 메시지 불러오기
    @GetMapping("/message/list")
    public ResponseEntity<ChatRoomMessage> getMessages(@AuthenticationPrincipal User user, @RequestParam String workspaceUUID, @RequestParam Long chatRoomId) {

        Workspace workspace = workspaceRepository.findByWorkspaceUUID(workspaceUUID).orElseThrow();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        ChatRoomUser chatRoomUser = chatRoomUserRepository.findByUserAndWorkspaceAndChatRoom(user, workspace, chatRoom).orElseThrow();

        ChatRoom chatRoom2 = chatRoomUser.getChatRoom();

        ChatRoomMessage chatRoomMessage = messageService.getMessages(chatRoom2);

        return ResponseEntity.ok(chatRoomMessage);
    }

    // 메시지 전송 및 저장
    @MessageMapping("/message") // /pub/message로 날린 데이터에 대해 /sub/chat/room + roomId 구독자들에게 메시지 전송
    public void sendAndSaveMessage(@Payload Message message) {

        ChatRoomMessage chatRoomMessage = chatRoomMessageRepository.findByMessage(message);
        ChatRoom chatRoom = chatRoomMessage.getChatRoom();

        // 메시지 전송
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatRoom.getChatRoomId(), message);

        // 메시지 저장
        messageService.saveMessage(chatRoom, message);
    }
}