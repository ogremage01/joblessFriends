package com.joblessfriend.jobfinder.chat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.chat.domain.UserChatRoomLastRead;
import com.joblessfriend.jobfinder.chat.domain.UserChatRoomLastReadId;

/**
 * {@link UserChatRoomLastRead} 엔티티에 대한 데이터 접근을 처리하는 JPA 리포지토리 인터페이스입니다.
 * 사용자가 특정 채팅방의 메시지를 마지막으로 읽은 시간 정보를 관리(CRUD)합니다.
 */
@Repository // 이 인터페이스가 Spring Data JPA 리포지토리임을 나타냅니다.
// UserChatRoomLastRead 엔티티를 관리하며, 복합 기본 키 타입은 UserChatRoomLastReadId 입니다.
public interface UserChatRoomLastReadRepository extends JpaRepository<UserChatRoomLastRead, UserChatRoomLastReadId> {

    /**
     * 특정 사용자 ID와 채팅방 ID에 해당하는 마지막 읽은 시간 정보를 조회합니다.
     *
     * @param userId 사용자(관리자 또는 일반 회원)의 ID
     * @param roomId 채팅방의 ID
     * @return Optional 객체로 래핑된 {@link UserChatRoomLastRead} 엔티티. 해당 정보가 없으면 Optional.empty()를 반환합니다.
     */
    Optional<UserChatRoomLastRead> findByUserIdAndRoomId(String userId, String roomId);

    // 필요에 따라 lastReadTimestamp보다 특정 시간 이후인 메시지 개수를 세는 등의 커스텀 쿼리 추가 가능
    // 예: @Query("SELECT COUNT(m) FROM ChatMessageEntity m WHERE m.roomId = :roomId AND m.sender <> :userId AND m.sendTime > :lastReadTime")
    // long countUnreadMessagesAfterTimestamp(@Param("roomId") String roomId, @Param("userId") String userId, @Param("lastReadTime") LocalDateTime lastReadTime);
    // 위와 같은 커스텀 쿼리는 ChatMessageRepository에 더 적합할 수 있습니다.
} 