package com.joblessfriend.jobfinder.company.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestBody; // 현재 사용되지 않으므로 주석 처리 또는 삭제
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody; // ResponseBody 임포트 추가 (메시지 조회에 필요)

import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.domain.UserChatRoomLastRead;
import com.joblessfriend.jobfinder.chat.repository.ChatMessageRepository;
import com.joblessfriend.jobfinder.chat.repository.UserChatRoomLastReadRepository;
import com.joblessfriend.jobfinder.chat.service.ChatService;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 기업 회원 채팅 관련 HTTP 요청을 처리하는 컨트롤러 클래스입니다.
 * 기업 회원의 채팅방 입장, 관리자와의 메시지 조회 등의 기능을 담당합니다.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/company/chat")
public class CompanyChatController {

	private final ChatService chatService; // 채팅 관련 비즈니스 로직을 처리하는 서비스
	private final ChatMessageRepository chatMessageRepository;
	private final UserChatRoomLastReadRepository userChatRoomLastReadRepository;

	/**
	 * 기업 회원의 채팅방 (주로 관리자와의 채팅)으로 입장하거나 생성하여 입장합니다.
	 *
	 * @param session 현재 HTTP 세션 객체. 기업 회원 로그인 정보를 가져오는 데 사용됩니다.
	 * @param model   View에 전달할 데이터를 담는 Model 객체. 채팅방 정보와 기업 이름을 전달합니다.
	 * @return 채팅방 화면의 경로를 반환합니다. 로그인하지 않은 경우 로그인 페이지로 리디렉션합니다.
	 */
	@GetMapping("/room")
	public String enterOrCreateRoom(HttpSession session, Model model) {
		log.info("[CompanyChatController] 기업 채팅방 입장/생성 요청");
		CompanyVo companyVo = (CompanyVo) session.getAttribute("userLogin"); // 세션에서 현재 로그인한 기업 회원 정보를 가져옵니다.

		if (companyVo == null) {
			log.warn("[CompanyChatController] 비로그인 기업 사용자의 채팅방 접근 시도. 로그인 페이지로 리디렉션합니다.");
			return "redirect:/auth/login"; // 로그인하지 않은 경우 로그인 페이지로 리디렉션합니다.
		}

		// 기업 ID를 기반으로 채팅방을 찾거나 생성합니다.
		ChatRoomVo room = chatService.companyCreateRoom(companyVo.getCompanyId());

		model.addAttribute("room", room); // View에 채팅방 정보를 전달합니다.
		model.addAttribute("companyName", companyVo.getCompanyName()); // View에 기업 이름을 전달합니다.
		log.info("[CompanyChatController] 기업 '{}'(ID:{})의 채팅방 '{}' 입장 처리 완료.", companyVo.getCompanyName(), companyVo.getCompanyId(), room.getRoomId());
		return "company/chat/chatView"; // 기업 회원용 채팅 화면 경로
	}

	/**
	 * 특정 채팅방(기업회원과 관리자 간)의 이전 메시지 내역을 조회합니다.
	 *
	 * @param roomId  메시지를 조회할 채팅방의 ID. URL 경로에서 가져옵니다.
	 * @param session 현재 HTTP 세션 객체. 사용자 인증 및 ID 확인에 사용됩니다.
	 * @return 성공 시 채팅 메시지 목록을 담은 ResponseEntity, 실패 시 에러 메시지를 담은 ResponseEntity를 반환합니다.
	 */
	@GetMapping("/messages/{roomId}")
	@ResponseBody // 이 어노테이션이 없으면 View를 찾으려고 시도할 수 있습니다.
	public ResponseEntity<?> getChatMessages(@PathVariable String roomId, HttpSession session) {
		log.info("[CompanyChatController] 기업 채팅방 '{}'의 메시지 내역 요청", roomId);

		Object userObj = session.getAttribute("userLogin"); // 세션에서 로그인 정보를 가져옵니다.
		if (userObj == null) {
			log.warn("[CompanyChatController] 비로그인 사용자의 기업 채팅 메시지 조회 시도. 채팅방 ID: {}", roomId);
			return ResponseEntity.status(401).body("로그인이 필요합니다."); // 401 Unauthorized
		}

		String companyUserId; // 기업을 식별할 ID (읽음 처리용)
		// 현재 로그인한 사용자가 기업 회원인지 확인하고, 요청된 roomId가 해당 기업의 채팅방이 맞는지 검증합니다.
		if (userObj instanceof CompanyVo) {
			CompanyVo company = (CompanyVo) userObj;
			// 기업 채팅방의 roomId는 일반적으로 기업 ID로 설정됩니다.
			if (company.getCompanyId() != Integer.parseInt(roomId)) { // 문자열 roomId를 정수로 변환하여 비교
				log.warn("[CompanyChatController] 기업 ID 불일치로 메시지 조회 거부. 세션 기업 ID: {}, 요청된 방 ID: {}", company.getCompanyId(), roomId);
				return ResponseEntity.status(403).body("접근 권한이 없습니다."); // 403 Forbidden
			}
			companyUserId = String.valueOf(company.getCompanyId()); // 읽음 처리를 위해 기업 ID를 문자열로 사용
		} else {
			log.warn("[CompanyChatController] userLogin 세션 객체가 CompanyVo 타입이 아님. 타입: {}", userObj.getClass().getName());
			return ResponseEntity.status(401).body("잘못된 사용자 세션입니다."); // 401 Unauthorized (또는 다른 적절한 상태 코드)
		}
		
		try {
		    // ChatService를 통해 메시지 목록을 가져옵니다.
		    // 기업 회원이 자신의 메시지를 읽는 것이므로, 두 번째 파라미터(adminId/userId)에 기업 ID를 전달하여 읽음 처리합니다.
		    List<ChatMessageVo> messages = chatService.getMessagesByRoomId(roomId, companyUserId);
		    log.info("[CompanyChatController] 기업 채팅방 '{}'의 메시지 {}건 조회 완료.", roomId, messages.size());
		    return ResponseEntity.ok(messages); // 성공 시 메시지 목록 반환 (200 OK)
		} catch (NumberFormatException e) {
		    log.error("[CompanyChatController] 채팅방 ID '{}'를 숫자로 변환 중 오류 발생", roomId, e);
		    return ResponseEntity.badRequest().body("잘못된 채팅방 ID 형식입니다."); // 400 Bad Request
		} catch (Exception e) {
		    log.error("[CompanyChatController] 기업 채팅방 '{}' 메시지 조회 중 오류 발생", roomId, e);
		    return ResponseEntity.internalServerError().body("메시지 조회 중 오류가 발생했습니다."); // 500 Internal Server Error
		}
	}

	/**
	 * 기업 회원의 안읽은 메시지 수를 확인합니다.
	 * 기업 회원은 자신의 companyId를 roomId로 하는 채팅방에서만 안읽은 메시지를 확인할 수 있습니다.
	 *
	 * @param session 현재 HTTP 세션 객체. 기업 회원 로그인 정보를 가져오는 데 사용됩니다.
	 * @return 안읽은 메시지 수를 담은 ResponseEntity를 반환합니다.
	 */
	@GetMapping("/unread-count")
	@ResponseBody
	public ResponseEntity<?> getUnreadMessageCount(HttpSession session) {
		log.debug("[CompanyChatController] 안읽은 메시지 수 확인 요청");

		Object userObj = session.getAttribute("userLogin");
		if (userObj == null) {
			log.warn("[CompanyChatController] 비로그인 사용자의 안읽은 메시지 수 확인 시도");
			return ResponseEntity.status(401).body("로그인이 필요합니다.");
		}

		if (!(userObj instanceof CompanyVo)) {
			log.warn("[CompanyChatController] userLogin 세션 객체가 CompanyVo 타입이 아님. 타입: {}", userObj.getClass().getName());
			return ResponseEntity.status(401).body("잘못된 사용자 세션입니다.");
		}

		try {
			CompanyVo companyVo = (CompanyVo) userObj;
			String roomId = String.valueOf(companyVo.getCompanyId()); // 기업의 채팅방 ID는 companyId
			String userId = String.valueOf(companyVo.getCompanyId()); // 사용자 ID도 companyId

			// 마지막 읽은 시간 조회
			Optional<UserChatRoomLastRead> lastReadOpt = userChatRoomLastReadRepository.findByUserIdAndRoomId(userId, roomId);
			Date lastReadTime = lastReadOpt.map(UserChatRoomLastRead::getLastReadTimestamp).orElse(new Date(0)); // 읽은 기록이 없으면 1970년 1월 1일

			// 마지막 읽은 시간 이후의 관리자 메시지 수 계산 (sender가 "관리자"인 메시지)
			long unreadCount = chatMessageRepository.countByRoomIdAndSenderNotAndSendTimeAfter(roomId, userId, lastReadTime);

			log.debug("[CompanyChatController] 기업 '{}'(ID:{})의 안읽은 메시지 수: {}", companyVo.getCompanyName(), companyVo.getCompanyId(), unreadCount);
			return ResponseEntity.ok().body(unreadCount);

		} catch (Exception e) {
			log.error("[CompanyChatController] 안읽은 메시지 수 확인 중 오류 발생", e);
			return ResponseEntity.internalServerError().body("안읽은 메시지 수 확인 중 오류가 발생했습니다.");
		}
	}

}
