package com.teamkrews.chat.controller;

import com.teamkrews.chat.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    // 특정 채팅방(chatRoomId)에 메시지 전송
    @MessageMapping("/chat/room/{chatRoomId}")
    public void sendMessageToSpecificUser(Message message, @DestinationVariable Long chatRoomId) {
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatRoomId, message);
    }
}