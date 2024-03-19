package com.teamkrews.chat.service;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.ChatRoomUser;
import com.teamkrews.User.model.User;
import com.teamkrews.chat.model.request.ChatRoomCreationRequest;
import com.teamkrews.chat.model.response.ChatRoomResponse;
import com.teamkrews.chat.repository.ChatRoomRepository;
import com.teamkrews.chat.repository.ChatRoomUserRepository;
import com.teamkrews.User.repository.UserRepository;
import com.teamkrews.workspace.model.Workspace;
import com.teamkrews.workspace.repository.WorkspaceRepository;
import java.util.List;
import java.util.stream.Collectors;
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

        // 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();
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
    public List<ChatRoomResponse> getChatRoomsByUserIdAndWorkspaceUUID(Long userId, String workspaceUUID) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Workspace workspace = workspaceRepository.findByWorkspaceUUID(workspaceUUID).orElseThrow(() -> new RuntimeException("워크스페이스를 찾을 수 없습니다."));

        // chatRoomRepository를 사용하여 채팅방 목록 조회
        List<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findByUserAndWorkspace(user, workspace);

        // 조회된 채팅방 목록을 ChatRoomResponse 리스트로 변환
        return chatRoomUsers.stream()
                .map(chatRoomUser -> {
                    ChatRoomResponse response = new ChatRoomResponse();
                    response.setChatRoomId(chatRoomUser.getChatRoom().getChatRoomId());
                    response.setWorkspaceUUID(chatRoomUser.getWorkspace().getWorkspaceUUID());
                    return response;
                })
                .collect(Collectors.toList());
    }
}
