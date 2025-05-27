package com.joblessfriend.jobfinder.chat.repository;

import com.joblessfriend.jobfinder.chat.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * {@link ChatRoomEntity}에 대한 데이터 접근을 처리하는 Spring Data JPA 리포지토리 인터페이스입니다.
 * 채팅방 정보의 CRUD 연산 및 특정 조건에 따른 조회 기능을 제공합니다.
 */
@Repository // 이 인터페이스가 Spring Data JPA 리포지토리임을 나타냅니다.
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, String> { // ChatRoomEntity를 관리하며, 기본 키 타입은 String(roomId)입니다.

    /**
     * 모든 채팅방 정보를 마지막 메시지 시간의 내림차순으로 조회합니다.
     * (최신 메시지가 오간 채팅방이 먼저 표시됩니다.)
     *
     * @return 정렬된 채팅방 엔티티 목록
     */
    List<ChatRoomEntity> findAllByOrderByLastMessageTimeDesc();

    /**
     * 회원 채팅방 (email 필드가 null이 아닌 채팅방) 목록을 마지막 메시지 시간의 내림차순으로 조회합니다.
     *
     * @return 정렬된 회원 채팅방 엔티티 목록
     */
    List<ChatRoomEntity> findByEmailIsNotNullOrderByLastMessageTimeDesc();

    /**
     * 기업 채팅방 (email 필드가 null인 채팅방) 목록을 마지막 메시지 시간의 내림차순으로 조회합니다.
     *
     * @return 정렬된 기업 채팅방 엔티티 목록
     */
    List<ChatRoomEntity> findByEmailIsNullOrderByLastMessageTimeDesc();
    
    // roomId로 Like 검색 (예: 특정 패턴을 가진 roomId를 찾을 때, 필요시 추가)
    // List<ChatRoomEntity> findByRoomIdContainingOrderByLastMessageTimeDesc(String roomIdPattern);
} 