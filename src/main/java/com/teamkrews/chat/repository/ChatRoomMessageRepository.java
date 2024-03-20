package com.teamkrews.chat.repository;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.ChatRoomMessage;
import com.teamkrews.chat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomMessageRepository extends JpaRepository<ChatRoomMessage, Long> {
    ChatRoomMessage findByChatRoom(ChatRoom chatRoom);
    ChatRoomMessage findByMessage(Message message);
}
