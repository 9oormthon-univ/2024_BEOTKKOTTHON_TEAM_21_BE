package com.teamkrews.chatRoomUser.service;

import com.teamkrews.chatRoomUser.model.ChatRoomUser;
import com.teamkrews.chatRoomUser.repository.ChatRoomUserRepository;
import com.teamkrews.global.exception.CustomException;
import com.teamkrews.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomUserService {
    private final ChatRoomUserRepository chatRoomUserRepository;

    public ChatRoomUser findById(final Long chatRoomUserId){
        Optional<ChatRoomUser> chatRoomUserOptional = chatRoomUserRepository.findById(chatRoomUserId);

        if(chatRoomUserOptional.isEmpty()){
            throw new CustomException(ErrorCode.CHAT_ROOM_USER_NOT_FOUND);
        }

        return chatRoomUserOptional.get();
    }
}
