package com.teamkrews.chat.controller;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.Message;
import com.teamkrews.chat.repository.MessageRepository;
import com.teamkrews.chat.service.ChatRoomService;
import com.teamkrews.chat.service.MessageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@Controller
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;
    private final MessageService messageService;
    private final ChatRoomService chatRoomService;

    // 메시지 저장
    @PostMapping("/chat/user/{userId}/chatRoom/{chatRoomId}/save")
    public ResponseEntity<Message> saveMessage(@RequestBody Message message) {
        Message savedMessage = messageService.saveMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    // 메시지 불러오기
    @GetMapping("/chat/user/{userId}/chatRoom/{chatRoomId}/get")
    public ResponseEntity<List<Message>> getMessagesByChatRoom(@PathVariable Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId);
        List<Message> messages = messageService.getMessagesByChatRoom(chatRoom);
        return ResponseEntity.ok(messages);
    }

    // 특정 채팅방(chatRoomId)에 메시지 전송
    @MessageMapping("/chat/room/{chatRoomId}")
    public void sendMessageToSpecificUser(Message message, @DestinationVariable Long chatRoomId) {
        // 메시지를 DB에 저장
        messageRepository.save(message);

        // 채팅방으로 메시지 전송
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatRoomId, message);
    }
}