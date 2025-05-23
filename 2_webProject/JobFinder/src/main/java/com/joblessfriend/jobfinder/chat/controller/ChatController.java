package com.joblessfriend.jobfinder.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.service.ChatService;

@Controller
public class ChatController {

	@Autowired
    private ChatService chatService;

    @PostMapping("chat/rooms")
    public ChatRoomVo createRoom(@RequestParam String name) {
       return chatService.createRoom(name);
    }

    @GetMapping("chat/rooms")
    public List<ChatRoomVo> findAllRoom() {
       return chatService.findAllRoom();
    }
	
}
