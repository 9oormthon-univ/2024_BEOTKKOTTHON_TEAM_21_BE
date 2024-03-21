package com.teamkrews.chat.controller;

import com.teamkrews.User.model.User;
import com.teamkrews.auth.controller.AuthenticationPrincipal;
import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.ChatRoomUser;
import com.teamkrews.chat.model.Message;
import com.teamkrews.chat.repository.ChatRoomUserRepository;
import com.teamkrews.chat.service.ChatRoomService;
import com.teamkrews.chat.service.MessageService;
import com.teamkrews.utill.ApiResponse;
import com.teamkrews.workspace.model.Workspace;
import com.teamkrews.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final ChatRoomService chatRoomService;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final WorkspaceService workspaceService;

    // 특정 채팅방의 메시지 불러오기
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<Message>>> getMessagesWithUserAndWorkspaceAndChatRoom(
            @AuthenticationPrincipal User user,
            @RequestParam String workspaceUUID,
            @RequestParam Long chatRoomId) {

        //ChatRoom에 대한 정보에 접근할 때 user가 ChatRoom에 대한 권한이 있는지 판단하는 로직 필요

        Workspace workspace = workspaceService.findByUUID(workspaceUUID);
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);

        ChatRoomUser chatRoomUser = chatRoomUserRepository.findByUserAndWorkspaceAndChatRoom(user, workspace, chatRoom).orElseThrow();

        ChatRoom chatRoom2 = chatRoomUser.getChatRoom();

        List<Message> messages = messageService.getMessages(chatRoom2);
        return ResponseEntity.ok(ApiResponse.success(messages));
    }

    // 메시지 전송 및 저장
    @MessageMapping("/{chatRoomId}") // /pub/message로 날린 데이터에 대해 /sub/chat/room + chatRoomId 구독자들에게 메시지 전송
    @SendTo("/chatRoom/{chatRoomId}")
    public void sendAndSaveMessage(@DestinationVariable Long chatRoomId, @Payload Message message) {
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
        // 메시지 저장
        messageService.saveMessage(chatRoom, message);
    }
}