package com.joblessfriend.jobfinder.chat.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;

/**
 * 채팅 관련 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 * 채팅방 생성, 조회, 메시지 전송, 읽지 않은 메시지 수 계산 등 핵심 기능을 명세합니다.
 */
public interface ChatService {
	
	/**
	 * 관리자용으로 모든 채팅방 목록을 조회합니다.
	 * 각 채팅방에는 해당 관리자가 읽지 않은 메시지 수가 포함될 수 있습니다.
	 * 채팅방은 마지막 메시지 시간의 내림차순(최신 메시지방 우선)으로 정렬됩니다.
	 *
	 * @param adminId 조회하는 관리자의 ID
	 * @return 정렬된 채팅방 정보 목록 ({@link ChatRoomVo}의 리스트)
	 */
	List<ChatRoomVo> findAllRoom(String adminId);
	
	/**
	 * 특정 ID를 가진 채팅방 정보를 조회합니다.
	 *
	 * @param roomId 조회할 채팅방의 고유 ID
	 * @return 조회된 채팅방 정보 ({@link ChatRoomVo}), 없으면 null 또는 Optional.empty() 처리 가능
	 */
	ChatRoomVo findRoomById(String roomId);
	
	/**
	 * 새로운 채팅방을 생성합니다. 주로 회원(일반 사용자)이 관리자와의 채팅을 시작할 때 사용됩니다.
	 * 채팅방의 ID는 주로 회원 식별자(예: 이메일)로 설정됩니다.
	 *
	 * @param name 채팅방의 이름 또는 회원 식별자 (이 값을 기반으로 roomId 및 방 이름이 결정됨)
	 * @return 생성된 채팅방 정보 ({@link ChatRoomVo})
	 */
	ChatRoomVo createRoom(String name); // 현재 파라미터 name은 userEmail 또는 roomId로 사용될 수 있음.
	
	/**
	 * 기업 회원을 위한 새로운 채팅방을 생성합니다. 주로 기업 회원이 관리자와의 채팅을 시작할 때 사용됩니다.
	 * 채팅방의 ID는 기업 ID로 설정됩니다.
	 *
	 * @param companyId 채팅방을 생성할 기업의 고유 ID
	 * @return 생성된 채팅방 정보 ({@link ChatRoomVo})
	 */
	ChatRoomVo companyCreateRoom(int companyId);
	
	/**
	 * 특정 웹소켓 세션으로 메시지를 전송합니다.
	 * 메시지 객체는 JSON으로 변환되어 전송됩니다.
	 *
	 * @param session 메시지를 전송할 대상 웹소켓 세션
	 * @param message 전송할 메시지 객체 (타입 T는 {@link ChatMessageVo} 등 다양할 수 있음)
	 * @param <T> 전송할 메시지의 타입
	 */
	<T> void sendMessage(WebSocketSession session, T message);

	/**
	 * 관리자용으로 일반 회원 채팅방 목록을 조회합니다.
	 * 각 채팅방에는 해당 관리자가 읽지 않은 메시지 수가 포함됩니다.
	 * 채팅방은 마지막 메시지 시간의 내림차순으로 정렬됩니다.
	 *
	 * @param adminId 조회하는 관리자의 ID
	 * @return 정렬된 일반 회원 채팅방 정보 목록 ({@link ChatRoomVo}의 리스트)
	 */
	List<ChatRoomVo> findMemberRooms(String adminId);
	
	/**
	 * 관리자용으로 기업 회원 채팅방 목록을 조회합니다.
	 * 각 채팅방에는 해당 관리자가 읽지 않은 메시지 수가 포함됩니다.
	 * 채팅방은 마지막 메시지 시간의 내림차순으로 정렬됩니다.
	 *
	 * @param adminId 조회하는 관리자의 ID
	 * @return 정렬된 기업 회원 채팅방 정보 목록 ({@link ChatRoomVo}의 리스트)
	 */
	List<ChatRoomVo> findCompanyRooms(String adminId);
	
	/**
	 * 특정 채팅방의 메시지 목록을 조회합니다. (정렬 순서나 읽음 처리 미포함 가능성 있음)
	 * 이 메소드의 역할이 {@link #getMessagesByRoomId(String, String)}와 중복될 수 있으므로,
	 * 사용 시 주의하거나 역할을 명확히 구분하여 사용하는 것이 좋습니다.
	 *
	 * @param roomId 조회할 채팅방의 ID
	 * @return 해당 채팅방의 메시지 목록 ({@link ChatMessageVo}의 리스트)
	 */
	List<ChatMessageVo> findMessagesByRoomId(String roomId);
	
	/**
	 * 특정 채팅방의 메시지 목록을 조회하며, 사용자의 읽음 상태를 업데이트합니다.
	 * 메시지는 전송 시간(sendTime) 오름차순(오래된 메시지 먼저)으로 정렬됩니다.
	 *
	 * @param roomId 조회할 채팅방의 ID
	 * @param userId 메시지를 조회하는 사용자(관리자, 회원, 기업 등)의 ID. 이 ID를 기준으로 읽음 처리됩니다.
	 * @return 정렬된 메시지 목록 ({@link ChatMessageVo}의 리스트)
	 */
	List<ChatMessageVo> getMessagesByRoomId(String roomId, String userId);

	// ChatMessageSavedListener에서 호출되어 DB와 메모리(activeChatRooms)의 lastMessageTime을 동기화합니다.
	void updateRoomLastMessageTime(String roomId, Date lastMessageTime);
}
