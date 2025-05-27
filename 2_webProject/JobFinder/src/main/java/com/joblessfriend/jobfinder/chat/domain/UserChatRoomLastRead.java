package com.joblessfriend.jobfinder.chat.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자가 특정 채팅방의 메시지를 마지막으로 읽은 시간을 기록하는 JPA 엔티티 클래스입니다.
 * 이 정보는 특정 채팅방에 대해 사용자가 읽지 않은 메시지 수를 계산하는 데 사용됩니다.
 * 복합 기본 키로 {@link UserChatRoomLastReadId} 클래스를 사용합니다.
 */
@Entity // 이 클래스가 JPA 엔티티임을 나타냅니다.
@Table(name = "UserChatRoomLastRead") // 데이터베이스의 "UserChatRoomLastRead" 테이블과 매핑됩니다. 실제 테이블명에 맞게 조정해야 할 수 있습니다.
@Getter // Lombok: 모든 필드에 대한 getter 메소드를 자동으로 생성합니다.
@Setter // Lombok: 모든 필드에 대한 setter 메소드를 자동으로 생성합니다.
@NoArgsConstructor // Lombok: 파라미터 없는 기본 생성자를 자동으로 생성합니다. (JPA 명세상 필요)
@AllArgsConstructor // Lombok: 모든 필드를 인자로 받는 생성자를 자동으로 생성합니다.
@Builder // Lombok: 빌더 패턴 코드를 자동으로 생성합니다.
@IdClass(UserChatRoomLastReadId.class) // 이 엔티티가 복합 기본 키를 사용하며, 해당 키 클래스를 지정합니다.
public class UserChatRoomLastRead {

    /**
     * 복합 기본 키의 일부로, 사용자의 고유 식별자입니다.
     * (예: 관리자 ID, 일반 회원 이메일 또는 ID)
     * {@link UserChatRoomLastReadId#userId}와 매핑됩니다.
     */
    @Id // 이 필드가 기본 키의 일부임을 나타냅니다.
    @Column(name = "user_id") // 데이터베이스 테이블의 "user_id" 컬럼과 매핑됩니다. 실제 컬럼명에 맞게 조정해야 합니다.
    private String userId;

    /**
     * 복합 기본 키의 일부로, 채팅방의 고유 식별자입니다.
     * {@link UserChatRoomLastReadId#roomId}와 매핑됩니다.
     */
    @Id // 이 필드가 기본 키의 일부임을 나타냅니다.
    @Column(name = "room_id") // 데이터베이스 테이블의 "room_id" 컬럼과 매핑됩니다.
    private String roomId;

    /**
     * 사용자가 해당 채팅방의 메시지를 마지막으로 읽은 시간의 타임스탬프입니다.
     * 이 필드는 null을 허용하지 않습니다.
     */
    @Column(name = "last_read_timestamp", nullable = false) // "last_read_timestamp" 컬럼과 매핑되며, null 값을 허용하지 않습니다.
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastReadTimestamp;

} 