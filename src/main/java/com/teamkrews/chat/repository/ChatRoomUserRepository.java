package com.teamkrews.chat.repository;

import com.teamkrews.chat.model.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

}