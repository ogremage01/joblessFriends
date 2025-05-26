package com.joblessfriend.jobfinder.chat.repository;

import com.joblessfriend.jobfinder.chat.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    // 특정 채팅방의 메시지를 시간순으로 조회
    List<ChatMessageEntity> findByRoomIdOrderBySendTimeAsc(String roomId);

    // 최근 n개 메시지를 시간 역순으로 조회
    List<ChatMessageEntity> findTop100ByRoomIdOrderBySendTimeDesc(String roomId);

	List<ChatMessageEntity> findByRoomId(String cleanRoomId);
}
