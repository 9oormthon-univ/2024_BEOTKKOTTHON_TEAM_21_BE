package com.teamkrews.chat.service;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.ChatRoomMessage;
import com.teamkrews.chat.model.Message;
import com.teamkrews.chat.repository.ChatRoomMessageRepository;
import com.teamkrews.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomMessageRepository chatRoomMessageRepository;


    // 특정 유저의 특정 워크스페이스의 특정 채팅방의 메시지 조회
    public ChatRoomMessage getMessages(ChatRoom chatRoom) {
        return chatRoomMessageRepository.findByChatRoom(chatRoom);
    }

    // 메시지 저장
    public void saveMessage(ChatRoom chatRoom, Message message) {
        messageRepository.save(message);

        ChatRoomMessage chatRoomMessage = new ChatRoomMessage();
        chatRoomMessage.setChatRoom(chatRoom);
        chatRoomMessage.setMessage(message);

        chatRoomMessageRepository.save(chatRoomMessage);
    }
}
