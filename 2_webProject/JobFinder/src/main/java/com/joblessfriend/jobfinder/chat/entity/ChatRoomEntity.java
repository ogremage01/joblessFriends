package com.joblessfriend.jobfinder.chat.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 채팅방 정보를 나타내는 JPA 엔티티 클래스입니다.
 * 데이터베이스의 'chat_room' 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "chat_room") // 데이터베이스 테이블명 (예: chat_room)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomEntity {

    /**
     * 채팅방의 고유 식별자입니다. (Primary Key)
     * 회원 채팅방의 경우 사용자 이메일, 기업 채팅방의 경우 기업 ID(문자열)가 사용됩니다.
     */
    @Id
    @Column(name = "room_id", length = 255)
    private String roomId;

    /**
     * 채팅방의 이름입니다.
     * 예: "회원 user@example.com의 채팅방", "[기업123] 기업명"
     */
    @Column(name = "name", length = 255)
    private String name;

    /**
     * 회원 채팅방의 경우, 해당 회원의 이메일 주소입니다.
     * 기업 채팅방의 경우 이 필드는 null이 됩니다.
     */
    @Column(name = "email", length = 255, nullable = true)
    private String email;

    /**
     * 해당 채팅방의 마지막 메시지가 전송된 시간입니다.
     * 채팅방 목록 정렬 등에 사용됩니다.
     */
    @Column(name = "last_message_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastMessageTime;
    
    // unreadCount 필드는 동적으로 계산되므로 엔티티에 포함하지 않습니다.
    // 웹소켓 세션 정보(sessions) 또한 런타임 데이터이므로 엔티티에 포함하지 않습니다.
} 