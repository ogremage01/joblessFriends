package com.joblessfriend.jobfinder.chat.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 채팅 메시지 정보를 담는 Value Object
 * 채팅 메시지의 타입, 내용, 발신자 정보 등을 관리
 */
@Getter
@Setter
public class ChatMessageVo {

    /**
     * 채팅 메시지 타입을 정의하는 열거형
     * ENTER: 채팅방 입장 메시지
     * TALK: 일반 대화 메시지
     */
    public enum MessageType {
        ENTER,  // 채팅방 입장
        TALK    // 일반 대화
    }

    // 메시지 타입 (ENTER 또는 TALK)
    private MessageType type;
    // 메시지가 속한 채팅방 ID
    private String roomId;
    // 메시지 발신자
    private String sender;
    // 메시지 내용
    private String message;
}
