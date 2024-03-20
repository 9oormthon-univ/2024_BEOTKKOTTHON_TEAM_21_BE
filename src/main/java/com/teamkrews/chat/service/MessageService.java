package com.teamkrews.chat.service;

import com.teamkrews.User.model.User;
import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.ChatRoomMessage;
import com.teamkrews.chat.model.ChatRoomUser;
import com.teamkrews.chat.model.Message;
import com.teamkrews.chat.repository.ChatRoomMessageRepository;
import com.teamkrews.chat.repository.ChatRoomUserRepository;
import com.teamkrews.chat.repository.MessageRepository;
import com.teamkrews.workspace.model.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatRoomMessageRepository chatRoomMessageRepository;

//    // 메시지 저장
//    public Message saveMessage() {
//        chatRoomMessageRepository.save(chatRoomMessage);
//        return null;
//    }

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
