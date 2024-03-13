package com.teamkrews.chat.service;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.Message;
import com.teamkrews.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByChatRoom(ChatRoom chatRoom) {
        return messageRepository.findByChatRoomOrderByCreatedAtAsc(chatRoom);
    }
}
