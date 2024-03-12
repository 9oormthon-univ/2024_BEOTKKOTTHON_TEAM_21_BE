package com.teamkrews.dm.controller;

import com.teamkrews.dm.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
클라이언트가 "/chat/message" 주제로 메시지를 보내면,
이 메시지는 해당 컨트롤러로 라우팅되어 처리되고,
처리된 결과는 다시 '/sub/chat/room/{roomId}' 주제로 클라이언트에게 전송
 */

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {

    @MessageMapping("/send")
    @SendTo("/sub/public") // 다시 클라이언트로 보낼 때 사용하는 경로
    public Message sendMessage(Message message) {
        return message;
    }

    @MessageMapping("/addUser")
    @SendTo("/sub/public")
    public Message addUser(Message message) {
        return message;
    }
}