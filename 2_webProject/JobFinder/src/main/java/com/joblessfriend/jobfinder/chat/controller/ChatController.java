package com.joblessfriend.jobfinder.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.service.ChatService;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * 일반적인 채팅 관련 기능을 제공하는 컨트롤러 클래스입니다.
 * (현재는 주로 초기 테스트 또는 간단한 채팅방 관리용으로 사용될 수 있습니다.)
 */
@Slf4j
@Controller
public class ChatController {

	private final ChatService chatService;

    /**
     * ChatController 생성자입니다.
     * Spring의 생성자 기반 의존성 주입을 통해 ChatService를 주입받습니다.
     * @param chatService 채팅 관련 비즈니스 로직을 처리하는 서비스
     */
    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * 새로운 채팅방을 생성합니다.
     * 주로 회원 또는 기업이 관리자와의 1:1 채팅을 시작할 때 사용될 수 있습니다.
     * @param name 생성할 채팅방의 이름 (주로 사용자의 ID 또는 고유 식별자).
     * @return 생성된 채팅방 정보 (ChatRoomVo).
     */
    @PostMapping("/chat/rooms")
    @ResponseBody
    public ChatRoomVo createRoom(@RequestParam String name) {
       log.info("[ChatController] 채팅방 생성 요청. 이름: {}", name);
       return chatService.createRoom(name);
    }

    /**
     * 현재 시스템에 존재하는 모든 채팅방 목록을 조회합니다.
     * (주의: 이 버전은 관리자 ID를 받지 않아, unreadCount가 정확하지 않을 수 있습니다.)
     * @return 모든 채팅방 정보 목록 (List<ChatRoomVo>).
     */
    @GetMapping("/chat/rooms")
    @ResponseBody
    public List<ChatRoomVo> findAllRoom() {
       log.info("[ChatController] 전체 채팅방 목록 조회 요청");
       return chatService.findAllRoom(null);
    }
	
}
