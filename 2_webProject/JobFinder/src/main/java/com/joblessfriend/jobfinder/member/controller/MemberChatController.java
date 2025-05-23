<<<<<<< HEAD
package com.joblessfriend.jobfinder.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.service.ChatService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member/chat")
@RequiredArgsConstructor
public class MemberChatController {

    private final ChatService chatService;

    @GetMapping("/room")
    public String enterOrCreateRoom(HttpSession session, Model model) {
        // 세션에서 사용자 ID를 가져와서 채팅방 이름으로 사용
        String username = (String) session.getAttribute("username");
        ChatRoomVo room = chatService.createRoom(username + "의 채팅방");

        model.addAttribute("room", room);
        return "member/chat/chatView";
    }
}
=======
//package com.joblessfriend.jobfinder.member.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
//import com.joblessfriend.jobfinder.chat.service.ChatService;
//import com.joblessfriend.jobfinder.member.domain.MemberVo;
//
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Controller
//@RequestMapping("/member/chat")
//@RequiredArgsConstructor
//public class MemberChatController {
//
//    private final ChatService chatService;
//
//    @GetMapping("/room")
//    public String enterOrCreateRoom(HttpSession session, Model model) {
//
//        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
//
//        if (memberVo == null) {
//            log.warn("User not logged in, redirecting to login page.");
//            return "redirect:/auth/login"; // 로그인 페이지로 리디렉션
//        }
//
//        ChatRoomVo room = chatService.createRoom(memberVo.getEmail() + "의 채팅방");
//
//        model.addAttribute("room", room);
//        return "member/chat/chatView";
//    }
//}
>>>>>>> origin/jhs
