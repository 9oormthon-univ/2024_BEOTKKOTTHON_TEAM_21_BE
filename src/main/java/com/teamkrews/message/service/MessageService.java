package com.teamkrews.message.service;

import com.teamkrews.chatroom.model.ChatRoom;
import com.teamkrews.message.model.Message;
import com.teamkrews.chatroom.service.ChatRoomService;
import com.teamkrews.message.model.request.MessageDTO;
import com.teamkrews.chatroom.repository.ChatRoomRepository;
import com.teamkrews.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;
    private final ModelMapper mapper;


    // 특정 유저의 특정 워크스페이스의 특정 채팅방의 메시지 조회
    public List<Message> getMessages(ChatRoom chatRoom) {
        List<Message> messageList = messageRepository.findAllByChatRoom(chatRoom);
        return messageList;
    }

    // 메시지 저장
    public Message saveMessage(Long chatRoomId, MessageDTO messageDTO) {
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);

        Message message = mapper.map(messageDTO, Message.class);
        message.setChatRoom(chatRoom);

        return messageRepository.save(message);
    }
}
