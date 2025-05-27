package com.joblessfriend.jobfinder.chat.domain;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@link UserChatRoomLastRead} 엔티티의 복합 기본 키(Composite Primary Key)를 정의하는 클래스입니다.
 * 사용자 ID와 채팅방 ID의 조합으로 각 레코드를 고유하게 식별합니다.
 * JPA에서 복합 키를 사용하기 위해서는 {@link Serializable} 인터페이스를 구현해야 하며,
 * `equals()`와 `hashCode()` 메소드를 올바르게 오버라이드해야 합니다.
 */
@Getter // Lombok: 모든 필드에 대한 getter 메소드를 자동으로 생성합니다.
@Setter // Lombok: 모든 필드에 대한 setter 메소드를 자동으로 생성합니다.
@NoArgsConstructor // Lombok: 파라미터 없는 기본 생성자를 자동으로 생성합니다.
@AllArgsConstructor // Lombok: 모든 필드를 인자로 받는 생성자를 자동으로 생성합니다.
public class UserChatRoomLastReadId implements Serializable {
    
    /**
     * 직렬화(Serialization) 시 사용되는 고유 버전 ID입니다.
     * 클래스 구조 변경 시 이 값을 변경하여 호환성 문제를 방지할 수 있습니다.
     */
    private static final long serialVersionUID = 1L; 

    // 사용자(관리자 또는 일반 회원)의 고유 식별자입니다.
    private String userId; 
    // 채팅방의 고유 식별자입니다.
    private String roomId;

    /**
     * 현재 객체와 다른 객체가 같은지 비교합니다.
     * userId와 roomId가 모두 같으면 동일한 객체로 간주합니다.
     *
     * @param o 비교할 객체
     * @return 두 객체가 같으면 true, 그렇지 않으면 false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // 동일한 인스턴스인지 확인
        // o가 null이거나 클래스 타입이 다르면 false
        if (o == null || getClass() != o.getClass()) return false;
        UserChatRoomLastReadId that = (UserChatRoomLastReadId) o; // 비교 대상 객체를 현재 타입으로 캐스팅
        // userId와 roomId 필드가 모두 같은지 Objects.equals를 사용하여 안전하게 비교
        return Objects.equals(userId, that.userId) && Objects.equals(roomId, that.roomId);
    }

    /**
     * 객체의 해시 코드를 반환합니다.
     * userId와 roomId를 기반으로 해시 코드를 생성합니다.
     * `equals()` 메소드가 true를 반환하는 두 객체는 반드시 동일한 해시 코드를 반환해야 합니다.
     *
     * @return 객체의 해시 코드
     */
    @Override
    public int hashCode() {
        // userId와 roomId를 사용하여 해시 코드를 생성 (Objects.hash는 null 안전함)
        return Objects.hash(userId, roomId);
    }
} 