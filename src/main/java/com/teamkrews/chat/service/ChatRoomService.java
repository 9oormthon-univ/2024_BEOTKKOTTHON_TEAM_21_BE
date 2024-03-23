package com.teamkrews.chat.service;

import com.teamkrews.User.repository.UserRepository;
import com.teamkrews.User.service.UserService;
import com.teamkrews.chat.model.*;
import com.teamkrews.User.model.User;
import com.teamkrews.chat.model.response.ChatRoomDetailResponse;
import com.teamkrews.chat.model.response.ChatRoomResponse;
import com.teamkrews.chat.model.response.UserInfoResponse;
import com.teamkrews.chat.repository.ChatRoomRepository;
import com.teamkrews.chat.repository.ChatRoomUserRepository;
import com.teamkrews.global.exception.CustomException;
import com.teamkrews.global.exception.ErrorCode;
import com.teamkrews.workspace.model.Workspace;

import java.util.ArrayList;
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
    private final UserRepository userRepository;

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
    public List<ChatRoomDetailResponse> getChatRoomsOfSent(User user, String workspaceUUID) {

        Workspace workspace = workspaceService.findByUUID(workspaceUUID);
        List<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findByUserAndWorkspaceAndIsCreator(user, workspace, 1);

        List<ChatRoomDetailResponse> chatRoomDetails = new ArrayList<>();
        for (ChatRoomUser chatRoomUser : chatRoomUsers) {
            ChatRoom chatRoom = chatRoomUser.getChatRoom();

            List<ChatRoomUser> chatRoomUsersReceived = chatRoomUserRepository.findByChatRoomAndIsCreator(chatRoom, 0);

            parseForResponse(chatRoomDetails, chatRoom, chatRoomUsersReceived);
        }

        return chatRoomDetails;
    }

    // 내가 받은 채팅방 조회
    public List<ChatRoomDetailResponse> getChatRoomsOfReceived(User user, String workspaceUUID) {

        Workspace workspace = workspaceService.findByUUID(workspaceUUID);
        List<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findByUserAndWorkspaceAndIsCreator(user, workspace, 0);

        List<ChatRoomDetailResponse> chatRoomDetails = new ArrayList<>();
        for (ChatRoomUser chatRoomUser : chatRoomUsers) {
            ChatRoom chatRoom = chatRoomUser.getChatRoom();

            List<ChatRoomUser> chatRoomUsersSent = chatRoomUserRepository.findByChatRoomAndIsCreator(chatRoom, 1);

            parseForResponse(chatRoomDetails, chatRoom, chatRoomUsersSent);
        }

        return chatRoomDetails;
    }

    private void parseForResponse(List<ChatRoomDetailResponse> chatRoomDetails, ChatRoom chatRoom,
                                  List<ChatRoomUser> chatRoomUsersSent) {
        for (ChatRoomUser userSent : chatRoomUsersSent) {
            ChatRoomDetailResponse chatRoomDetailResponse = new ChatRoomDetailResponse();
            chatRoomDetailResponse.setChatRoomId(chatRoom.getChatRoomId());
            chatRoomDetailResponse.setChatRoomUserId(userSent.getId());

            UserInfoResponse userInfoResponse = new UserInfoResponse();
            userInfoResponse.setNickName(userSent.getUser().getNickName());
            userInfoResponse.setProfileImageUrl(userSent.getUser().getProfileImageUrl());

            chatRoomDetailResponse.setTargetUser(userInfoResponse);

            chatRoomDetails.add(chatRoomDetailResponse);
        }
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
}
