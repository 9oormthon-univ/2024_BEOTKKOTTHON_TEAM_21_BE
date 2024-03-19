package com.teamkrews.chat.controller;

import com.teamkrews.User.model.User;
import com.teamkrews.User.repository.UserRepository;
import com.teamkrews.auth.controller.AuthenticationPrincipal;
import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.model.ChatRoomUser;
import com.teamkrews.chat.model.request.ChatRoomCreationRequest;
import com.teamkrews.chat.model.response.ChatRoomResponse;
import com.teamkrews.chat.service.ChatRoomService;
import com.teamkrews.chat.service.ChatRoomUserService;
import com.teamkrews.utill.ApiResponse;
import com.teamkrews.workspace.model.Workspace;
import com.teamkrews.workspace.model.request.WorkspaceUUIDRequest;
import com.teamkrews.workspace.repository.WorkspaceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/chatRoom")
@Slf4j
public class ChatRoomController {
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    private final ChatRoomService chatRoomService;
    private final ChatRoomUserService chatRoomUserService;

    // 1:1 채팅방 생성 - 내가 피드백 보낸 경우
    // @RequestBody는 하나의 메서드에 여러 개일 수 없음 -> 따라서, 두 개의 Long 타입 인자로 받으려면 하나의 DTO 안에 넣어줘야 함!
    @PostMapping("/create/chatRoom")
    public ResponseEntity<ApiResponse<ChatRoomUser>> createChatRoom(@RequestBody ChatRoomCreationRequest request) {
        try {
            ChatRoom chatRoom = chatRoomService.createChatRoomWithUser(request);

            User user = userRepository.findById(request.getCurrentUserId()).orElseThrow();
            Workspace workspace = workspaceRepository.findByWorkspaceUUID(request.getWorkspaceUUID()).orElseThrow();

            ChatRoomUser chatRoomUser = chatRoomUserService.createChatRoomUser(user, workspace, chatRoom);

            return ResponseEntity.ok(ApiResponse.success(chatRoomUser));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 채팅방 목록 조회
    // 나중에 내가 먼저 보낸 채팅방 & 받은 채팅방으로 분리하기
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ChatRoomResponse>>> getChatRooms(@AuthenticationPrincipal User user, @RequestBody WorkspaceUUIDRequest workspaceRequest) {

        log.info("aa");
        Long userId = user.getId();
        log.info(userId.toString());
        String workspaceUUID = workspaceRequest.getWorkspaceUUID();
        log.info(workspaceUUID);
        List<ChatRoomResponse> chatRoomResponses = chatRoomService.getChatRoomsByUserIdAndWorkspaceUUID(userId, workspaceUUID);

        return ResponseEntity.ok(ApiResponse.success(chatRoomResponses));
    }

}