package com.joblessfriend.jobfinder.chat.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageVo {


    // 채팅 타입 (채팅방 입장, 채팅 메시지 전송)
    public enum MessageType {
       ENTER, TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
	
}
