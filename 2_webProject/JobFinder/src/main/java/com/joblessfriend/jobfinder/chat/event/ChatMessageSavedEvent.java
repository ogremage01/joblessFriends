package com.joblessfriend.jobfinder.chat.event;

import org.springframework.context.ApplicationEvent;
import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import lombok.Getter;
// import lombok.RequiredArgsConstructor; // ApplicationEvent를 상속하므로 직접 생성자 정의

/**
 * 채팅 메시지가 DB에 저장될 필요가 있을 때 발행되는 이벤트 클래스입니다.
 * Spring의 ApplicationEvent를 상속받아 이벤트 시스템에 통합됩니다.
 */
@Getter
// @RequiredArgsConstructor // ApplicationEvent를 상속하고 source를 받으므로 생성자 직접 정의
public class ChatMessageSavedEvent extends ApplicationEvent { // ApplicationEvent 상속
    
    /**
     * 직렬화에 사용될 버전 UID 입니다.
     */
    private static final long serialVersionUID = 1L; // ApplicationEvent는 Serializable을 구현하므로 serialVersionUID 추가
    
    private final transient ChatMessageVo chatMessage; // transient 추가 (source가 직렬화 대상이 아닐 수 있음)

    /**
     * ChatMessageSavedEvent의 생성자입니다.
     *
     * @param source 이벤트 발생시킨 객체 (일반적으로 이벤트를 발행한 서비스 또는 컴포넌트)
     * @param chatMessage 저장될 채팅 메시지 정보 (ChatMessageVo)
     */
    public ChatMessageSavedEvent(Object source, ChatMessageVo chatMessage) {
        super(source); // 부모 클래스(ApplicationEvent)의 생성자 호출하여 source 설정
        this.chatMessage = chatMessage;
    }
}