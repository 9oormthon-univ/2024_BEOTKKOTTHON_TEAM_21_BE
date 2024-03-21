package com.teamkrews.chat.service;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.Message;
import com.teamkrews.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;


    // 특정 유저의 특정 워크스페이스의 특정 채팅방의 메시지 조회
    public List<Message> getMessages(ChatRoom chatRoom) {
        List<Message> messageList = messageRepository.findAllByChatRoom(chatRoom);
        return messageList;
    }

    // 메시지 저장
    public void saveMessage(ChatRoom chatRoom, Message message) {
        message.setChatRoom(chatRoom);
        messageRepository.save(message);
    }
}
