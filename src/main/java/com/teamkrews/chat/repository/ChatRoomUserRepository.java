package com.teamkrews.chat.repository;

import com.teamkrews.User.model.User;
import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.ChatRoomUser;
import com.teamkrews.workspace.model.Workspace;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    List<ChatRoomUser> findByUserAndWorkspace(User user, Workspace workspace);
}