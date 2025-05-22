package com.joblessfriend.jobfinder.chat.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.service.ChatService;

public class ChatController {

	
    private ChatService chatService;

    @PostMapping("/rooms")
    public ChatRoomVo createRoom(@RequestParam String name) {
       return chatService.createRoom(name);
    }

    @GetMapping("/rooms")
    public List<ChatRoomVo> findAllRoom() {
       return chatService.findAllRoom();
    }
	
}
