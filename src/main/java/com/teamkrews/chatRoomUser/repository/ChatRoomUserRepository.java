package com.teamkrews.chatRoomUser.repository;

import com.teamkrews.User.model.User;
import com.teamkrews.chatroom.model.ChatRoom;
import com.teamkrews.chatRoomUser.model.ChatRoomUser;
import com.teamkrews.workspace.model.Workspace;
import java.util.List;
import java.util.Optional;

import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    List<ChatRoomUser> findByUserAndWorkspace(User user, Workspace workspace);
    Optional<ChatRoomUser> findByUserAndWorkspaceAndChatRoom(User user, Workspace workspace, ChatRoom chatRoom);

    List<ChatRoomUser> findByUserAndWorkspaceAndIsCreator(User user, Workspace workspace, int isCreator);

    @Query("SELECT cru FROM ChatRoomUser cru LEFT JOIN FETCH cru.lastMessage lm WHERE cru.user = :user AND cru.workspace = :workspace AND cru.isCreator = :isCreator ORDER BY lm.createdAt ASC")
    List<ChatRoomUser> findByUserAndWorkspaceAndIsCreatorOrderByLastMessage(@Param("user") User user, @Param("workspace") Workspace workspace, @Param("isCreator") int isCreator);
    List<ChatRoomUser> findByChatRoomAndIsCreator(ChatRoom chatRoom, int isCreator);

    List<ChatRoomUser> findAllByChatRoomAndNewState(ChatRoom chatRoom, Boolean newState);

    List<ChatRoomUser> findAllByChatRoom(ChatRoom chatRoom);

    @Query("SELECT cru FROM ChatRoomUser cru WHERE cru.chatRoom = :chatRoom AND cru.user <> :user")
    List<ChatRoomUser> findByChatRoomAndNotUser(@Param("chatRoom") ChatRoom chatRoom, @Param("user") User user);

    List<ChatRoomUser> findAllByWorkspace(Workspace workspace);
}