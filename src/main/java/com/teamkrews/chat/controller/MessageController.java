//package com.teamkrews.chat.controller;
//
//import com.teamkrews.chat.model.ChatRoom;
//import com.teamkrews.chat.model.Message;
//import com.teamkrews.chat.repository.ChatRoomRepository;
//import com.teamkrews.chat.repository.MessageRepository;
//import com.teamkrews.chat.service.MessageService;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//
//@RequiredArgsConstructor
//@Controller
//@RequestMapping("/message")
//public class MessageController {
//
//    private final SimpMessagingTemplate messagingTemplate;
//    private final MessageRepository messageRepository;
//    private final MessageService messageService;
//    private final ChatRoomRepository chatRoomRepository;
//
//     메시지 저장
//    @PostMapping("")
//    public ResponseEntity<Message> saveMessage(@RequestBody Message message, Long chatRoomId) {
//
//        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid chatRoomId: " + chatRoomId));
//
//        message.setChatRoom(chatRoom);
//
//        Message savedMessage = messageService.saveMessage(message);
//
//        return ResponseEntity.ok(savedMessage);
//    }
//
//    // 메시지 불러오기
//    @GetMapping("/load")
//    public ResponseEntity<List<Message>> getMessagesByChatRoom(@RequestParam Long chatRoomId) {
//        List<Message> messages = messageService.getMessagesByChatRoomId(chatRoomId);
//        return ResponseEntity.ok(messages);
//    }
//
//    // 특정 채팅방(chatRoomId)에 메시지 전송
//    @MessageMapping("/room/{chatRoomId}")
//    public void sendMessageToSpecificUser(Message message, @DestinationVariable Long chatRoomId) {
//        // 메시지를 DB에 저장
//        messageRepository.save(message);
//
//        // 채팅방으로 메시지 전송
//        messagingTemplate.convertAndSend("/sub/chat/room/" + chatRoomId, message);
//    }
//}