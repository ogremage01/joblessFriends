package com.joblessfriend.jobfinder.company.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.service.ChatService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company/chat")
public class CompanyChatController {

    private final ChatService chatService;

    @GetMapping("/room")
    public String enterOrCreateRoom(HttpSession session, Model model) {
        // 세션에서 사용자 ID를 가져와서 채팅방 이름으로 사용
        String username = (String) session.getAttribute("username");
        ChatRoomVo room = chatService.createRoom(username + "의 채팅방");

        model.addAttribute("room", room);
        return "company/chat/room"; // -> /WEB-INF/views/member/chat/room.jsp
    }
}
