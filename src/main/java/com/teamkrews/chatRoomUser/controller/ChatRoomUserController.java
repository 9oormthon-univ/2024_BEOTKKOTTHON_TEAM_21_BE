package com.teamkrews.chatRoomUser.controller;

import com.teamkrews.User.model.User;
import com.teamkrews.auth.controller.AuthenticationPrincipal;
import com.teamkrews.chatRoomUser.model.response.ChatRoomUserResponses;
import com.teamkrews.chatRoomUser.service.ChatRoomUserService;
import com.teamkrews.chatroom.model.ChatRoomCreationDto;
import com.teamkrews.chatroom.model.request.ChatRoomCreationRequest;
import com.teamkrews.chatroom.model.response.ChatRoomResponse;
import com.teamkrews.chatroom.service.ChatRoomService;
import com.teamkrews.utill.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//tem
@RequiredArgsConstructor
@RestController
@RequestMapping("/chatRoomUser")
@Slf4j
public class ChatRoomUserController {
    private final ChatRoomUserService chatRoomUserService;
    private final ModelMapper mapper;

    // 1:1 채팅방 생성 - 내가 피드백 보낸 경우
    // @RequestBody는 하나의 메서드에 여러 개일 수 없음 -> 따라서, 두 개의 Long 타입 인자로 받으려면 하나의 DTO 안에 넣어줘야 함!
    @PostMapping("/check/{chatRoomUserId}")
    public ResponseEntity<ApiResponse<Void>> createChatRoom(@AuthenticationPrincipal User user,
                                                            @PathVariable Long chatRoomUserId) {
        log.info("isNew Check Event !!");

        chatRoomUserService.checkChatRoomUser(chatRoomUserId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
