package com.teamkrews.chat.service;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.ChatRoomUser;
import com.teamkrews.User.model.User;
import com.teamkrews.chat.model.request.ChatRoomCreationRequest;
import com.teamkrews.chat.repository.ChatRoomRepository;
import com.teamkrews.chat.repository.ChatRoomUserRepository;
import com.teamkrews.User.repository.UserRepository;
import com.teamkrews.workspace.model.Workspace;
import com.teamkrews.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatRoomService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final WorkspaceRepository workspaceRepository;

    // 1:1 채팅방 생성
    @Transactional
    public ChatRoom createChatRoomWithUser(ChatRoomCreationRequest request) {

        User currentUser = userRepository.findById(request.getCurrentUserId()).orElseThrow();
        User targetUser = userRepository.findById(request.getTargetUserId()).orElseThrow();
        Workspace workspace = workspaceRepository.findById(request.getWorkspaceId()).orElseThrow();

        // 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setWorkspace(workspace);
        chatRoomRepository.save(chatRoom);

        // chatRoomUser - chatRoom / user 연결
        connectUserToChatRoom(chatRoom, currentUser);
        connectUserToChatRoom(chatRoom, targetUser);

        return chatRoom;
    }

    private void connectUserToChatRoom(ChatRoom chatRoom, User user) {
        ChatRoomUser chatRoomUser = new ChatRoomUser();
        chatRoomUser.setChatRoom(chatRoom);
        chatRoomUser.setUser(user);
        chatRoomUserRepository.save(chatRoomUser);
    }

    // 채팅방 조회
    public ChatRoom getChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다: " + chatRoomId));
    }
}
