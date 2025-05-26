package com.joblessfriend.jobfinder.chat.event;

import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatMessageSavedEvent {
    private final ChatMessageVo chatMessage;
}