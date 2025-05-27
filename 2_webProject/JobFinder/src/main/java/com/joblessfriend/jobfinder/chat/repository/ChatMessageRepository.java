package com.joblessfriend.jobfinder.chat.repository;

import com.joblessfriend.jobfinder.chat.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 채팅 메시지 데이터에 접근하기 위한 JPA 리포지토리 인터페이스입니다.
 * {@link ChatMessageEntity}의 CRUD(Create, Read, Update, Delete) 연산을 비롯하여
 * 특정 조건에 따른 메시지 조회 및 개수 계산 메소드를 정의합니다.
 */
@Repository // 이 인터페이스가 Spring Data JPA 리포지토리임을 나타냅니다.
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> { // ChatMessageEntity를 관리하며, 기본 키 타입은 Long입니다.

    /**
     * 특정 채팅방 ID에 해당하는 모든 메시지를 전송 시간 오름차순으로 조회합니다.
     * (오래된 메시지가 먼저 표시됩니다.)
     *
     * @param roomId 조회할 채팅방의 ID
     * @return 해당 채팅방의 메시지 엔티티 목록 (시간순 정렬)
     */
    List<ChatMessageEntity> findByRoomIdOrderBySendTimeAsc(String roomId);

    /**
     * 특정 채팅방 ID에 해당하는 최근 100개의 메시지를 전송 시간 내림차순으로 조회합니다.
     * (최신 메시지가 먼저 표시됩니다. 주로 미리보기 등에 사용될 수 있습니다.)
     *
     * @param roomId 조회할 채팅방의 ID
     * @return 해당 채팅방의 최근 메시지 100개 엔티티 목록 (시간 역순 정렬)
     */
    List<ChatMessageEntity> findTop100ByRoomIdOrderBySendTimeDesc(String roomId);

	/**
     * 특정 채팅방 ID에 해당하는 모든 메시지를 조회합니다. (정렬 순서는 보장되지 않음)
     * 정렬이 필요하다면 `findByRoomIdOrderBySendTimeAsc` 등을 사용해야 합니다.
     *
     * @param roomId 조회할 채팅방의 ID (정제된 ID 사용 권장)
     * @return 해당 채팅방의 메시지 엔티티 목록
     */
	List<ChatMessageEntity> findByRoomId(String roomId); // 파라미터명을 cleanRoomId에서 roomId로 변경 (일관성)

    /**
     * 특정 채팅방에서, 특정 발신자를 제외하고, 명시된 시간 이후에 전송된 메시지의 개수를 계산합니다.
     * 주로 특정 사용자의 읽지 않은 메시지 수를 계산하는 데 사용됩니다.
     * Oracle DB에서 LocalDateTime 변환 문제를 해결하기 위해 명시적 쿼리를 사용합니다.
     *
     * @param roomId 확인할 채팅방의 ID
     * @param sender 제외할 발신자의 ID (예: 관리자 자신의 ID)
     * @param sendTime 기준 시간 (이 시간 이후의 메시지를 카운트)
     * @return 조건을 만족하는 메시지의 개수
     */
    @Query("SELECT COUNT(c) FROM ChatMessageEntity c WHERE c.roomId = :roomId AND c.sender <> :sender AND c.sendTime > :sendTime")
    long countByRoomIdAndSenderNotAndSendTimeAfter(@Param("roomId") String roomId, @Param("sender") String sender, @Param("sendTime") Date sendTime);

    /**
     * 특정 채팅방에서, 특정 발신자를 제외한 모든 메시지의 개수를 계산합니다.
     * 사용자가 해당 채팅방을 한 번도 읽지 않았거나, 마지막 읽은 시간 정보가 없을 경우에 사용될 수 있습니다.
     *
     * @param roomId 확인할 채팅방의 ID
     * @param sender 제외할 발신자의 ID
     * @return 조건을 만족하는 메시지의 개수
     */
    long countByRoomIdAndSenderNot(String roomId, String sender);
}
