package com.teamkrews.chatRoomUser.repository;

import com.teamkrews.User.model.User;
import com.teamkrews.chatroom.model.ChatRoom;
import com.teamkrews.chatRoomUser.model.ChatRoomUser;
import com.teamkrews.workspace.model.Workspace;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    List<ChatRoomUser> findByUserAndWorkspace(User user, Workspace workspace);

    Optional<ChatRoomUser> findByUserAndWorkspaceAndChatRoom(User user, Workspace workspace, ChatRoom chatRoom);

    List<ChatRoomUser> findByUserAndWorkspaceAndIsCreator(User user, Workspace workspace, int isCreator);

    List<ChatRoomUser> findByChatRoomAndIsCreator(ChatRoom chatRoom, int isCreator);

    List<ChatRoomUser> findAllByChatRoomAndNewState(ChatRoom chatRoom, Boolean newState);
}