package com.joblessfriend.jobfinder.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.service.ChatService;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/company/chat")
public class CompanyChatController {

    private final ChatService chatService;

    @GetMapping("/room")
    public String enterOrCreateRoom(HttpSession session, Model model) {
        CompanyVo companyVo = (CompanyVo) session.getAttribute("userLogin");
        
        if (companyVo == null) {
            log.warn("Company not logged in, redirecting to login page.");
            return "redirect:/auth/login";
        }
        
        ChatRoomVo room = chatService.companyCreateRoom(companyVo.getCompanyId());
        
        model.addAttribute("room", room);
        model.addAttribute("companyName", companyVo.getCompanyName());
        return "company/chat/chatView";
    }
}
