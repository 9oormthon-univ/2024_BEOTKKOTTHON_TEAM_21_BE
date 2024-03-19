package com.teamkrews.chat.service;

import com.teamkrews.User.model.User;
import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.ChatRoomUser;
import com.teamkrews.chat.repository.ChatRoomUserRepository;
import com.teamkrews.workspace.model.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomUserService {

    private final ChatRoomUserRepository chatRoomUserRepository;

    public ChatRoomUser createChatRoomUser(User user, Workspace workspace, ChatRoom chatRoom) {
        ChatRoomUser chatRoomUser = new ChatRoomUser();
        chatRoomUser.setChatRoom(chatRoom);
        chatRoomUser.setUser(user);
        chatRoomUser.setWorkspace(workspace);

        chatRoomUserRepository.save(chatRoomUser);

        return chatRoomUser;
    }
}
