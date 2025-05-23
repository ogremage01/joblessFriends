package com.joblessfriend.jobfinder.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/chat")
@RequiredArgsConstructor
public class AdminChatController {

    private final ChatService chatService;

    @GetMapping("/rooms")
    @ResponseBody
    public ResponseEntity<List<ChatRoomVo>> getRooms(@RequestParam String type) {
        log.info("채팅방 목록 요청 - type: {}", type);
        try {
            List<ChatRoomVo> rooms;
            if ("member".equals(type)) {
                rooms = chatService.findMemberRooms();
            } else if ("company".equals(type)) {
                rooms = chatService.findCompanyRooms();
            } else {
                rooms = chatService.findAllRoom();
            }
            return ResponseEntity.ok(rooms);
        } catch (Exception e) {
            log.error("채팅방 목록 조회 실패", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/messages/{roomId}")
    @ResponseBody
    public ResponseEntity<List<ChatMessageVo>> getRoomMessages(@PathVariable String roomId) {
        log.info("채팅 메시지 목록 요청 - roomId: {}", roomId);
        try {
            List<ChatMessageVo> messages = chatService.findMessagesByRoomId(roomId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            log.error("채팅 메시지 조회 실패", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ResponseEntity<ChatRoomVo> getRoom(@PathVariable String roomId) {
        log.info("채팅방 정보 요청 - roomId: {}", roomId);
        try {
            ChatRoomVo room = chatService.findRoomById(roomId);
            if (room != null) {
                return ResponseEntity.ok(room);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("채팅방 정보 조회 실패", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // View 반환용 컨트롤러
    @GetMapping("/view")
    public String chatView() {
        return "admin/chat/adminChatListView";
    }
}
