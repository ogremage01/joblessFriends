package com.joblessfriend.jobfinder.chat.listener;

import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import com.joblessfriend.jobfinder.chat.entity.ChatMessageEntity;
import com.joblessfriend.jobfinder.chat.repository.ChatMessageRepository;
import com.joblessfriend.jobfinder.chat.event.ChatMessageSavedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatMessageEventListener {

    private final ChatMessageRepository chatMessageRepository;

    @Async // 비동기 저장
    @EventListener
    public void handleChatMessageSavedEvent(ChatMessageSavedEvent event) {
        ChatMessageVo vo = event.getChatMessage();

        try {
            // VO → Entity 변환
            ChatMessageEntity entity = ChatMessageEntity.builder()
                    .roomId(vo.getRoomId())
                    .sender(vo.getSender())
                    .message(vo.getMessage())
                    .sendTime(vo.getSendTime() != null ? vo.getSendTime() : new Date()) 	 // null 방지
                    .build();

            chatMessageRepository.save(entity);
            log.debug("채팅 메시지 저장 완료 - roomId: {}, sender: {}", vo.getRoomId(), vo.getSender());

        } catch (Exception e) {
            log.error("채팅 메시지 저장 실패 - roomId: {}, 에러: {}", vo.getRoomId(), e.getMessage());
        }
    }
}
