package com.joblessfriend.jobfinder.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.service.ChatService;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member/chat")
@RequiredArgsConstructor
public class MemberChatController {

    private final ChatService chatService;

    @GetMapping("/room")
    public String enterOrCreateRoom(HttpSession session, Model model) {
        
        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
        ChatRoomVo room = chatService.createRoom(memberVo.getEmail() + "의 채팅방");

        model.addAttribute("room", room);
        return "member/chat/chatView";
    }
}
