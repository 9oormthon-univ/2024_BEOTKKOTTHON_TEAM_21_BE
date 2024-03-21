package com.teamkrews.chat.repository;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatRoom(ChatRoom chatRoom);
}

