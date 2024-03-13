package com.teamkrews.chat.service;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.ChatRoomUser;
import com.teamkrews.chat.model.User;
import com.teamkrews.chat.repository.ChatRoomRepository;
import com.teamkrews.chat.repository.ChatRoomUserRepository;
import com.teamkrews.chat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository,
                           UserRepository userRepository, ChatRoomUserRepository chatRoomUserRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomUserRepository = chatRoomUserRepository;
        this.userRepository = userRepository;
    }

    // 1:1 채팅방 생성
    public ChatRoom createChatRoomWithUser(Long userId1, Long userId2) {

        // 사용자 검색
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId1));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId2));

        // 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom = chatRoomRepository.save(chatRoom);

        // User - ChatRoom 관계 설정
        ChatRoomUser chatRoomUser1 = new ChatRoomUser();
        chatRoomUser1.setUser(user1);
        chatRoomUser1.setChatRoom(chatRoom);
        chatRoomUserRepository.save(chatRoomUser1);

        ChatRoomUser chatRoomUser2 = new ChatRoomUser();
        chatRoomUser2.setUser(user2);
        chatRoomUser2.setChatRoom(chatRoom);
        chatRoomUserRepository.save(chatRoomUser2);

        return chatRoom;
    }

    // 채팅방 조회
    public ChatRoom getChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다: " + chatRoomId));
    }
}
