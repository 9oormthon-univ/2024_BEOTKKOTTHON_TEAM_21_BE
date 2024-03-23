package com.teamkrews.chatroom.service;

import com.teamkrews.User.service.UserService;
import com.teamkrews.chatRoomUser.model.ChatRoomUser;
import com.teamkrews.chatRoomUser.model.response.ChatRoomUserResponses;
import com.teamkrews.chatRoomUser.service.ChatRoomUserService;
import com.teamkrews.chatroom.model.*;
import com.teamkrews.User.model.User;
import com.teamkrews.chatroom.model.response.ChatRoomResponse;
import com.teamkrews.chatroom.repository.ChatRoomRepository;
import com.teamkrews.chatRoomUser.repository.ChatRoomUserRepository;
import com.teamkrews.global.exception.CustomException;
import com.teamkrews.global.exception.ErrorCode;
import com.teamkrews.message.model.Message;
import com.teamkrews.workspace.model.Workspace;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.teamkrews.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatRoomService {
    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final WorkspaceService workspaceService;
    private final ChatRoomUserService chatRoomUserService;

    public ChatRoom findById(final Long chatRoomId){
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(chatRoomId);

        if(chatRoomOptional.isEmpty()){
            throw new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND);
        }

        return chatRoomOptional.get();
    }


    // 1:1 채팅방 생성
    @Transactional
    public ChatRoomResponse createChatRoomWithUser(ChatRoomCreationDto dto) {
        Long creatorUserId = dto.getCreatorUserId();
        List<Long> userIds = dto.getUserIds();

        userIds.add(creatorUserId);
        HashSet<Long> userIdsSet = new HashSet<>(userIds);

        Workspace workspace = workspaceService.findByUUID(dto.getWorkspaceUUID());

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setUserCnt(Long.valueOf(userIdsSet.size()));

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        userIdsSet.stream().forEach((id)->{
            User user = userService.getById(id);
            int isCreator = 0;

            if(id == creatorUserId)
                isCreator = 1;

            createAndSaveChatRoomUser(savedChatRoom, user, workspace, isCreator);
        });

        ChatRoomResponse chatRoomResponse = new ChatRoomResponse();
        chatRoomResponse.setChatRoomId(chatRoom.getChatRoomId());
        chatRoomResponse.setWorkspaceUUID(dto.getWorkspaceUUID());

        return chatRoomResponse;
    }

    private void createAndSaveChatRoomUser(ChatRoom chatRoom, User user, Workspace workspace, int isCreator) {
        ChatRoomUser chatRoomUser = new ChatRoomUser();
        chatRoomUser.setChatRoom(chatRoom);
        chatRoomUser.setUser(user);
        chatRoomUser.setWorkspace(workspace);
        chatRoomUser.setIsCreator(isCreator);
        chatRoomUserRepository.save(chatRoomUser);
    }

    // 내가 보낸 채팅방 조회
    public ChatRoomUserResponses getChatRoomsOfSent(User user, String workspaceUUID) {

        Workspace workspace = workspaceService.findByUUID(workspaceUUID);
        List<ChatRoomUser> chatRoomUserSent = chatRoomUserRepository.findByUserAndWorkspaceAndIsCreator(user, workspace, 1);

        ChatRoomUserResponses chatRoomUserResponses = chatRoomUserService.convertToResponses(chatRoomUserSent);
        return chatRoomUserResponses;
    }

    // 내가 받은 채팅방 조회
    public ChatRoomUserResponses getChatRoomsOfReceived(User user, String workspaceUUID) {
        Workspace workspace = workspaceService.findByUUID(workspaceUUID);
        List<ChatRoomUser> chatRoomUserReceived = chatRoomUserRepository.findByUserAndWorkspaceAndIsCreator(user, workspace, 0);

        ChatRoomUserResponses chatRoomUserResponses = chatRoomUserService.convertToResponses(chatRoomUserReceived);
        return chatRoomUserResponses;
    }

    @Transactional
    public void setNewStateTrue(ChatRoomNewStateTrueDto dto){
        ChatRoom chatRoom = findById(dto.getChatRoomId());
        List<ChatRoomUser> chatRoomUserList = chatRoomUserRepository.findAllByChatRoomAndNewState(chatRoom, Boolean.FALSE);

        chatRoomUserList.stream().forEach(
                (chatRoomUser)->{
                    chatRoomUser.setNewState(dto.getNewState());
                }
        );
    }

    @Transactional
    public void setNewStateFalse(ChatRoomNewStateFalseDto dto){

        ChatRoomUser chatRoomUser = chatRoomUserService.findById(dto.getChatRoomUserId());
        chatRoomUser.setNewState(dto.getNewState());
    }

    @Transactional
    public void updateLastMessage(Long chatRoomId, Message message){
        ChatRoom chatRoom = findById(chatRoomId);

        List<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findAllByChatRoom(chatRoom);

        chatRoomUsers.forEach((chatRoomUser)->{
            chatRoomUser.setLastMessage(message);
        });
    }
}
