package com.joblessfriend.jobfinder.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/chat")
@RequiredArgsConstructor
public class AdminChatController {

    private final ChatService chatService;

    @GetMapping("/rooms")
    public String roomList(Model model) {
        List<ChatRoomVo> rooms = chatService.findAllRoom();
        model.addAttribute("rooms", rooms);
        return "admin/chat/adminChatListView"; // -> /WEB-INF/views/admin/chat/rooms.jsp
    }

    @GetMapping("/room")
    public String enterRoom(@RequestParam String roomId, Model model) {
        model.addAttribute("room", chatService.findRoomById(roomId));
        return "admin/chat/room"; // -> /WEB-INF/views/admin/chat/room.jsp
    }
}
