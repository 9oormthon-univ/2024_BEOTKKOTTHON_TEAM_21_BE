package com.teamkrews.dm.controller;

import com.teamkrews.dm.model.ChatRoom;
import com.teamkrews.dm.repository.ChatRoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


// 프론트랑 API 맞춰가며 개발하기
// 채팅 화면 View
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
@Slf4j
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    // 채팅 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        log.info("Get controller");
        return "/dm/room";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("list/chatRooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }

    // 채팅방 생성 (정상작동 - 24/03/12)
    @PostMapping("create/chatRoom")
    @ResponseBody
    public ChatRoom createRoom() {
        return chatRoomRepository.createChatRoom();
    }

    // 채팅방 입장
    @GetMapping("/enter/{chatRoomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/dm/roomdetail";
    }

    // 채팅방 알림
    @GetMapping("/notification")
    public String notification(Model model, @PathVariable String roomId) {
        return "null";
    }

    // 채팅방 삭제
    @DeleteMapping("/delete/{chatRoomId}")
    public String deleteRoom(Model model, @PathVariable String roomId) {
        return "null";
    }
}