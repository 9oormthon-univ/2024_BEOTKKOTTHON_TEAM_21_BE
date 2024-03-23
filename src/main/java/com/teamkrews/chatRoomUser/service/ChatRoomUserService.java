package com.teamkrews.chatRoomUser.service;

import com.teamkrews.User.model.User;
import com.teamkrews.User.model.UserInfos;
import com.teamkrews.User.service.UserService;
import com.teamkrews.chatRoomUser.model.ChatRoomUser;
import com.teamkrews.chatRoomUser.model.response.ChatRoomUserResponse;
import com.teamkrews.chatRoomUser.model.response.ChatRoomUserResponses;
import com.teamkrews.chatRoomUser.repository.ChatRoomUserRepository;
import com.teamkrews.global.exception.CustomException;
import com.teamkrews.global.exception.ErrorCode;
import com.teamkrews.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomUserService {
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final MessageService messageService;
    private final UserService userService;
    public ChatRoomUser findById(final Long chatRoomUserId){
        Optional<ChatRoomUser> chatRoomUserOptional = chatRoomUserRepository.findById(chatRoomUserId);

        if(chatRoomUserOptional.isEmpty()){
            throw new CustomException(ErrorCode.CHAT_ROOM_USER_NOT_FOUND);
        }

        return chatRoomUserOptional.get();
    }

    public ChatRoomUserResponse convertToResponse(ChatRoomUser chatRoomUser, UserInfos userInfos){
        ChatRoomUserResponse chatRoomUserResponse = new ChatRoomUserResponse();

        chatRoomUserResponse.setChatRoomUserId(chatRoomUser.getId());
        chatRoomUserResponse.setChatRoomId(chatRoomUser.getChatRoom().getChatRoomId());
        chatRoomUserResponse.setLastMessage(messageService.convertMessageResponse(chatRoomUser.getLastMessage()));
        chatRoomUserResponse.setTargetUsers(userInfos);

        return chatRoomUserResponse;
    }

    public ChatRoomUserResponses convertToResponses(List<ChatRoomUser> chatRoomUserList){
        List<ChatRoomUserResponse> chatRoomUserResponseList = chatRoomUserList.stream().map(
                (chatRoomUser) -> {
                    List<ChatRoomUser> otherChatRoomUsers = chatRoomUserRepository.
                            findByChatRoomAndNotUser(chatRoomUser.getChatRoom(), chatRoomUser.getUser());

                    List<User> otherUsers = otherChatRoomUsers.stream().map((otherChatRoomUser) ->
                            otherChatRoomUser.getUser()
                    ).collect(Collectors.toList());

                    UserInfos otherUserInfos = userService.convertToInfos(otherUsers);

                    return convertToResponse(chatRoomUser, otherUserInfos);
                }

        ).collect(Collectors.toList());

        return new ChatRoomUserResponses(chatRoomUserResponseList);
    }
 }
