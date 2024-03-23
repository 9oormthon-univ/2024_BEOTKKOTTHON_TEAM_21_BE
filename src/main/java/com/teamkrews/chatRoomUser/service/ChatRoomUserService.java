package com.teamkrews.chatRoomUser.service;

import com.teamkrews.User.model.User;
import com.teamkrews.User.model.UserInfos;
import com.teamkrews.User.service.UserService;
import com.teamkrews.chatRoomUser.model.ChatRoomUser;
import com.teamkrews.chatRoomUser.model.response.ChatRoomUserResponse;
import com.teamkrews.chatRoomUser.model.response.ChatRoomUserResponses;
import com.teamkrews.chatRoomUser.repository.ChatRoomUserRepository;
import com.teamkrews.chatroom.model.ChatRoom;
import com.teamkrews.global.exception.CustomException;
import com.teamkrews.global.exception.ErrorCode;
import com.teamkrews.message.service.MessageService;
import com.teamkrews.workspace.model.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        chatRoomUserResponse.setLastMessage(messageService.convertMessageResponse(chatRoomUser.getLastMessage(), null));
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

    @Transactional
    public void joinGroupChatRoom(User user, Workspace workspace){
        List<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findAllByWorkspace(workspace);

        ChatRoomUser groupChatRoomUser = null;
        for (ChatRoomUser chatRoomUser : chatRoomUsers) {
            if(chatRoomUser.getChatRoom().getIsGroup()){
                groupChatRoomUser = chatRoomUser;
                break;
            }
        }

        if (groupChatRoomUser == null) return;

        ChatRoom groupChatRoom = groupChatRoomUser.getChatRoom();
        groupChatRoom.setUserCnt(groupChatRoom.getUserCnt() + 1);

        ChatRoomUser chatRoomUser = new ChatRoomUser();
        chatRoomUser.setChatRoom(groupChatRoom);
        chatRoomUser.setUser(user);
        chatRoomUser.setWorkspace(workspace);
        chatRoomUser.setIsCreator(0);
        chatRoomUserRepository.save(chatRoomUser);
    }
 }
