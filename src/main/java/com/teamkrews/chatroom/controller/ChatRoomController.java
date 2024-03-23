package com.teamkrews.chatroom.controller;

import com.teamkrews.User.model.User;
import com.teamkrews.auth.controller.AuthenticationPrincipal;
import com.teamkrews.chatroom.model.ChatRoomCreationDto;
import com.teamkrews.chatroom.model.request.ChatRoomCreationRequest;
import com.teamkrews.chatroom.model.response.ChatRoomDetailResponse;
import com.teamkrews.chatroom.model.response.ChatRoomResponse;
import com.teamkrews.chatroom.service.ChatRoomService;
import com.teamkrews.utill.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final ChatRoomService chatRoomService;
    private final ModelMapper mapper;

    // 1:1 채팅방 생성 - 내가 피드백 보낸 경우
    // @RequestBody는 하나의 메서드에 여러 개일 수 없음 -> 따라서, 두 개의 Long 타입 인자로 받으려면 하나의 DTO 안에 넣어줘야 함!
    @PostMapping("")
    public ResponseEntity<ApiResponse<ChatRoomResponse>> createChatRoom(@AuthenticationPrincipal User user,
                                                                @RequestBody ChatRoomCreationRequest request) {
        ChatRoomCreationDto dto = mapper.map(request, ChatRoomCreationDto.class);
        dto.setCreatorUserId(user.getId());

        ChatRoomResponse response = chatRoomService.createChatRoomWithUser(dto);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 내가 보낸 채팅방 목록 조회
    @GetMapping("/sent")
    public ResponseEntity<ApiResponse<List<ChatRoomDetailResponse>>> getChatRoomsOfSent(@AuthenticationPrincipal User user, @RequestParam String workspaceUUID) {
        List<ChatRoomDetailResponse> chatRoomDetailResponse = chatRoomService.getChatRoomsOfSent(user, workspaceUUID);

        return ResponseEntity.ok(ApiResponse.success(chatRoomDetailResponse));
    }

    // 내가 받은 채팅방 목록 조회
    @GetMapping("/received")
    public ResponseEntity<ApiResponse<List<ChatRoomDetailResponse>>> getChatRoomsOfReceived(@AuthenticationPrincipal User user, @RequestParam String workspaceUUID) {
        List<ChatRoomDetailResponse> chatRoomDetailResponse = chatRoomService.getChatRoomsOfReceived(user, workspaceUUID);

        return ResponseEntity.ok(ApiResponse.success(chatRoomDetailResponse));
    }
}