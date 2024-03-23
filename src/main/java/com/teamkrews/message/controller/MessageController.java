package com.teamkrews.message.controller;

import com.teamkrews.User.model.User;
import com.teamkrews.auth.controller.AuthenticationPrincipal;
import com.teamkrews.chatroom.model.ChatRoom;
import com.teamkrews.chatroom.model.ChatRoomNewStateTrueDto;
import com.teamkrews.chatRoomUser.model.ChatRoomUser;
import com.teamkrews.message.model.Message;
import com.teamkrews.message.model.request.MessageDTO;
import com.teamkrews.message.model.response.MessageResponse;
import com.teamkrews.chatRoomUser.repository.ChatRoomUserRepository;
import com.teamkrews.chatroom.service.ChatRoomService;
import com.teamkrews.message.service.MessageService;
import com.teamkrews.openAI.model.request.MessageTranslatorDTO;
import com.teamkrews.openAI.service.MessageTranslatorService;
import com.teamkrews.utill.ApiResponse;
import com.teamkrews.workspace.model.Workspace;
import com.teamkrews.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController {

    private final MessageService messageService;
    private final ChatRoomService chatRoomService;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final WorkspaceService workspaceService;
    private final MessageTranslatorService messageTranslatorService;
    private final ModelMapper modelMapper;

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
    @MessageMapping("/message/{chatRoomId}") // /pub/message/{chatRoomId}로 날린 데이터에 대해 /sub/message/{chatRoomId} 구독자들에게 메시지 전송
    @SendTo("/message/room/{chatRoomId}")
    public MessageResponse sendAndSaveMessage(@DestinationVariable Long chatRoomId, @Payload MessageDTO messageDTO) {
        // 메시지 말투 변환

        log.info("Message Catch !!");
        String transformedMessage = messageTranslatorService.transformMessage(messageDTO);
        messageDTO.setContent(transformedMessage);


        // 메시지 저장
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
        Message message = messageService.saveMessage(chatRoom, messageDTO);
        chatRoomService.updateLastMessage(chatRoomId, message);
        chatRoomService.setNewStateTrue(new ChatRoomNewStateTrueDto(chatRoomId));
        MessageResponse messageResponse = messageService.convertMessageResponse(message);

        return messageResponse;
    }

    // 말투 변환 테스트
    @PostMapping("/test")
    public String translateMessage(@RequestBody MessageTranslatorDTO messageTranslatorDTO) {
        return messageTranslatorService.transformMessage(messageTranslatorDTO);
    }
}