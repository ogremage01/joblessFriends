package com.joblessfriend.jobfinder.admin.controller;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import com.joblessfriend.jobfinder.admin.domain.AdminVo;

import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<?> getRooms(@RequestParam(required = false) String type, HttpSession session) {
        log.info("채팅방 목록 요청 - type: {}", type);

        AdminVo admin = (AdminVo) session.getAttribute("userLogin");
        if (admin == null) {
            log.warn("Unauthorized access to admin chat rooms");
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Unauthorized access"));
        }
        String adminId = admin.getAdminId();
        if (adminId == null || adminId.isEmpty()) {
            log.warn("Admin ID not found in session");
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Admin ID not found"));
        }

        try {
            List<ChatRoomVo> rooms = chatService.findAllRoom(adminId);
            if (rooms == null) rooms = new ArrayList<>();
            return ResponseEntity.ok(rooms);
        } catch (Exception e) {
            log.error("채팅방 목록 조회 실패", e);
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Internal server error"));
        }
    }

    @GetMapping("/messages/{roomId}")
    @ResponseBody
    public ResponseEntity<?> getRoomMessages(@PathVariable String roomId, HttpSession session) {
        log.info("채팅 메시지 목록 요청 - roomId: {}", roomId);
        
        AdminVo admin = (AdminVo) session.getAttribute("userLogin");
        if (admin == null) {
            log.warn("Unauthorized access to admin chat messages");
            return ResponseEntity.status(401).body("Unauthorized access");
        }
        
        String adminId = admin.getAdminId();
        if (adminId == null || adminId.isEmpty()) {
            log.warn("Admin ID not found in session for messages");
            return ResponseEntity.status(401).body("Admin ID not found");
        }
        
        try {
            // getMessagesByRoomId로 변경하고 adminId 전달
            List<ChatMessageVo> messages = chatService.getMessagesByRoomId(roomId, adminId);
            log.info("전송받은 메시지 개수: {}", messages.size()); // System.out.println 대신 logger 사용
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            log.error("채팅 메시지 조회 실패", e);
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ResponseEntity<?> getRoom(@PathVariable String roomId, HttpSession session) {
        log.info("채팅방 정보 요청 - roomId: {}", roomId);
        
        // 관리자 세션 확인
        AdminVo admin = (AdminVo) session.getAttribute("userLogin");;
        if (admin == null) {
            log.warn("Unauthorized access to admin chat room");
            return ResponseEntity.status(401).body("Unauthorized access");
        }
        
        try {
            ChatRoomVo room = chatService.findRoomById(roomId);
            if (room != null) {
                return ResponseEntity.ok(room);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("채팅방 정보 조회 실패", e);
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    /**
     * 관리자의 전체 안읽은 채팅 메시지 수를 조회합니다.
     * 사이드바에서 안읽은 메시지 배지 표시에 사용됩니다.
     *
     * @param session 현재 HTTP 세션 객체. 관리자 로그인 정보를 가져오는 데 사용됩니다.
     * @return 전체 안읽은 메시지 수를 담은 ResponseEntity를 반환합니다.
     */
    @GetMapping("/unreadCount")
    @ResponseBody
    public ResponseEntity<?> getUnreadMessageCount(HttpSession session) {
        log.debug("[AdminChatController] 관리자 안읽은 메시지 수 확인 요청");

        AdminVo admin = (AdminVo) session.getAttribute("userLogin");
        if (admin == null) {
            log.warn("[AdminChatController] 비로그인 사용자의 안읽은 메시지 수 확인 시도");
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        String adminId = admin.getAdminId();
        if (adminId == null || adminId.isEmpty()) {
            log.warn("[AdminChatController] 관리자 ID가 세션에서 찾을 수 없음");
            return ResponseEntity.status(401).body("관리자 ID를 찾을 수 없습니다.");
        }

        try {
            // 모든 채팅방의 안읽은 메시지 수를 합산
            List<ChatRoomVo> allRooms = chatService.findAllRoom(adminId);
            int totalUnreadCount = 0;
            
            if (allRooms != null) {
                totalUnreadCount = allRooms.stream()
                    .mapToInt(ChatRoomVo::getUnreadCount)
                    .sum();
            }

            log.debug("[AdminChatController] 관리자 '{}'의 전체 안읽은 메시지 수: {}", adminId, totalUnreadCount);
            
            Map<String, Integer> response = new HashMap<>();
            response.put("unreadCount", totalUnreadCount);
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("[AdminChatController] 안읽은 메시지 수 확인 중 오류 발생. 관리자: {}", adminId, e);
            return ResponseEntity.internalServerError().body("안읽은 메시지 수 확인 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/view")
    public String chatView(HttpSession session) {
        // 관리자 세션 확인
        AdminVo admin = (AdminVo) session.getAttribute("userLogin");
        if (admin == null) {
            log.warn("Unauthorized access to admin chat view");
            return "redirect:/auth/login";
        }
        return "admin/chat/adminChatListView";
    }
}
