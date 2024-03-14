package com.teamkrews.chat.repository;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoomOrderByCreatedAtAsc(ChatRoom chatRoom);

}

