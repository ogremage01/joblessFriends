package com.joblessfriend.jobfinder.chat.handler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import jakarta.annotation.PreDestroy; // Spring Boot 3.x / Jakarta EE 9+
// import javax.annotation.PreDestroy; // 주석 처리

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.service.ChatService;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.admin.domain.AdminVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 웹소켓 연결 및 메시지 처리를 담당하는 핸들러 클래스입니다.
 * {@link TextWebSocketHandler}를 상속받아 텍스트 기반 웹소켓 메시지를 처리합니다.
 * 사용자 인증, 세션 관리, 메시지 라우팅, 비활성 세션 정리 등의 기능을 수행합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper;
	private final ChatService chatService;
	
	// 활성 웹소켓 세션을 저장하는 맵입니다. 세션 ID를 키로, WebSocketSession 객체를 값으로 가집니다.
    // (주의: ChatRoomVo 내부의 sessions와 중복 관리될 수 있으므로, 역할 명확화 또는 통합 필요)
    private final Map<String, WebSocketSession> activeSessions = new ConcurrentHashMap<>();

	// 세션별 마지막 활동 시간을 추적하는 맵입니다. 세션 ID를 키로, 마지막 활동 시간(timestamp)을 값으로 가집니다.
	private final Map<String, Long> sessionLastActivityMap = new ConcurrentHashMap<>();
	// 비활성 세션 검사를 위한 스케줄러입니다.
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	// 세션 비활성 시간 제한 값 (밀리초 단위). 현재 1분으로 설정되어 있습니다.
	private static final long INACTIVITY_TIMEOUT = 60 * 1000; // 1분
	// 비활성 검사 작업이 스케줄링 되었는지 여부를 나타내는 플래그
	private volatile boolean inactivityCheckerScheduled = false;

	/**
	 * 웹소켓 연결이 성공적으로 수립된 후 호출되는 메소드입니다.
	 * 세션 정보를 로깅하고, 사용자 타입에 따라 인증 및 초기 설정을 수행합니다.
	 * 인증되지 않은 사용자의 경우 연결을 종료합니다.
	 * 또한, 세션 활동 시간을 기록하고 비활성 검사 스케줄러를 시작합니다.
	 *
	 * @param session 새로 연결된 웹소켓 세션
	 * @throws Exception 예외 발생 시
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		log.info("[WebSocketHandler] 웹소켓 연결 수립. 세션 ID: {}, 원본 속성: {}", session.getId(), session.getAttributes());
		
		String userType = (String) session.getAttributes().get("userType");
		Object userLogin = session.getAttributes().get("userLogin");

		boolean authenticated = false;
		if ("admin".equals(userType) && userLogin instanceof AdminVo) { 
			AdminVo admin = (AdminVo) userLogin;
            session.getAttributes().put("adminId", admin.getAdminId());
			log.info("[WebSocketHandler] 관리자 연결됨. 세션 ID: {}, 관리자 ID: {}", session.getId(), admin.getAdminId());
			authenticated = true;
		} else if ("company".equals(userType) && userLogin instanceof CompanyVo) {
			CompanyVo company = (CompanyVo) userLogin;
            session.getAttributes().put("companyId", String.valueOf(company.getCompanyId()));
            session.getAttributes().put("companyName", company.getCompanyName()); 
			log.info("[WebSocketHandler] 기업 회원 '{}'(ID:{}, Name:{}) 연결됨. 세션 ID: {}", company.getCompanyName(), company.getCompanyId(), company.getCompanyName(), session.getId());
			authenticated = true;
		} else if ("member".equals(userType) && userLogin instanceof MemberVo) {
			MemberVo member = (MemberVo) userLogin;
            session.getAttributes().put("memberEmail", member.getEmail());
			log.info("[WebSocketHandler] 일반 회원 '{}' 연결됨. 세션 ID: {}", member.getEmail(), session.getId());
			authenticated = true;
		}
		
		if (authenticated) {
		    activeSessions.put(session.getId(), session); 
			updateSessionActivity(session);
			startInactivityCheckerIfNeeded(); 
		} else {
			log.warn("[WebSocketHandler] 인증되지 않은 사용자 연결 시도. 세션 ID: {}, userType: {}, userLogin: {}. 연결 종료.", 
			         session.getId(), userType, userLogin);
			session.close(CloseStatus.POLICY_VIOLATION.withReason("User not authenticated or invalid user type"));
		}
	}

	/**
	 * 웹소켓 클라이언트로부터 텍스트 메시지를 수신했을 때 호출되는 메소드입니다.
	 * 수신된 페이로드(JSON 문자열)를 {@link ChatMessageVo}로 변환하고, 
	 * 사용자 인증 상태를 확인한 후 {@link #handleChatMessage(WebSocketSession, ChatMessageVo)}를 호출하여 실제 메시지 처리를 위임합니다.
	 * 메시지 처리 중 예외 발생 시 에러 메시지를 클라이언트에 전송합니다.
	 *
	 * @param session 메시지를 수신한 웹소켓 세션
	 * @param message 수신된 텍스트 메시지 (JSON 페이로드 포함)
	 * @throws Exception 예외 발생 시
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.debug("[WebSocketHandler] 메시지 수신. 세션 ID: {}, 페이로드: {}", session.getId(), payload);
		
		try {
			updateSessionActivity(session);
			ChatMessageVo chatMessage = objectMapper.readValue(payload, ChatMessageVo.class);
			
			String userType = (String) session.getAttributes().get("userType");
			Object userLogin = session.getAttributes().get("userLogin"); // 인증된 사용자 정보

            // 세션에 저장된 사용자 식별자 가져오기 (afterConnectionEstablished에서 설정한 값)
            String sessionUserId = null;
            // 디버깅 로그: userType, userLogin 객체 타입, 세션에 저장된 각 사용자 ID 속성 값 확인
            log.debug("[WebSocketHandler] 사용자 식별자 확인 시작. userType: {}, userLogin class: {}, session adminId: {}, session companyId: {}, session companyName: {}, session memberEmail: {}",
                userType,
                (userLogin != null ? userLogin.getClass().getName() : "N/A"),
                session.getAttributes().get("adminId"),
                session.getAttributes().get("companyId"),
                session.getAttributes().get("companyName"),
                session.getAttributes().get("memberEmail")
            );

            if ("admin".equals(userType) && userLogin instanceof AdminVo) {
                // 관리자 세션의 경우: "adminId" 속성에서 관리자 ID를 가져옵니다.
                // afterConnectionEstablished에서 AdminVo의 getAdminId() 반환값을 그대로 저장했을 수 있으므로,
                // ClassCastException을 방지하기 위해 String.valueOf()를 사용하여 안전하게 문자열로 변환합니다.
                Object adminIdObj = session.getAttributes().get("adminId");
                if (adminIdObj != null) {
                    sessionUserId = String.valueOf(adminIdObj); // adminId를 문자열로 변환하여 사용
                    log.info("[WebSocketHandler] 관리자 사용자 확인됨. 세션에서 가져온 adminId 원본: {}, 변환된 sessionUserId: {}", adminIdObj, sessionUserId);
                } else {
                     log.warn("[WebSocketHandler] userType이 'admin'이지만 세션에서 'adminId' 속성을 찾을 수 없습니다. 세션 ID: {}", session.getId());
                }
            } else if ("company".equals(userType) && userLogin instanceof CompanyVo) {
                // 기업회원 세션의 경우: "companyId" 속성에서 기업 ID를 가져와 sessionUserId로 사용합니다.
                sessionUserId = (String) session.getAttributes().get("companyId");
                log.debug("[WebSocketHandler] 기업 사용자 확인됨. 세션에서 가져온 companyId: {}, 세션에 저장된 companyName: {}", 
                          sessionUserId, session.getAttributes().get("companyName"));
            } else if ("member".equals(userType) && userLogin instanceof MemberVo) {
                // 일반회원 세션의 경우: "memberEmail" 속성에서 이메일을 가져와 sessionUserId로 사용합니다.
                sessionUserId = (String) session.getAttributes().get("memberEmail");
                log.debug("[WebSocketHandler] 일반 회원 확인됨. 세션에서 가져온 memberEmail: {}", 
                          sessionUserId);
            }

			if (sessionUserId != null) { // 세션에 유효한 사용자 ID가 있다면 인증된 것으로 간주
			    log.debug("[WebSocketHandler] {} (ID:{}) 메시지 처리 준비. 세션 ID: {}", userType, sessionUserId, session.getId());
			    handleChatMessage(session, chatMessage, userType, sessionUserId); // userType과 sessionUserId 전달
			} else {
			    log.warn("[WebSocketHandler] 세션에서 사용자 식별자 조회 불가. 세션 ID: {}, userType: {}. 연결 종료.",
			             session.getId(), userType);
			    sendErrorMessageAndClose(session, "세션 정보가 유효하지 않습니다. 다시 로그인해주세요.", CloseStatus.POLICY_VIOLATION);
			}
			
		} catch (Exception e) {
			log.error("[WebSocketHandler] 메시지 처리 중 오류 발생. 세션 ID: {}, 페이로드: {}. 에러: {}", session.getId(), payload, e.getMessage(), e);
			sendErrorMessageAndClose(session, "메시지 처리 중 오류가 발생했습니다: " + e.getMessage(), CloseStatus.SERVER_ERROR);
		}
	}

	/**
	 * 실제 채팅 메시지를 처리하는 내부 메소드입니다.
	 * 메시지 발신자(sender) 정보가 없는 경우 세션 정보를 바탕으로 설정합니다.
	 * 메시지 타입(ENTER, TALK)과 사용자 타입에 따라 채팅방 ID를 설정하거나 생성합니다.
	 * 최종적으로 채팅방 정보를 조회하고, {@link ChatRoomVo#handleActions(WebSocketSession, ChatMessageVo, ChatService)}를 호출하여
	 * 해당 채팅방의 모든 참여자에게 메시지를 전송하고 관련 로직을 수행합니다.
	 *
	 * @param session 메시지를 보낸 웹소켓 세션
	 * @param chatMessage 처리할 채팅 메시지 객체 (ChatMessageVo)
	 * @throws Exception 예외 발생 시
	 */
	private void handleChatMessage(WebSocketSession session, ChatMessageVo chatMessage, String userTypeFromSession, String sessionUserId) throws Exception {
        log.debug("[WS_HandleMsg] 처리 시작: roomId={}, senderField={}, type={}, msg='{}', sessionUserType={}, sessionUserId={}, sessionId={}", 
                chatMessage.getRoomId(), chatMessage.getSender(), chatMessage.getType(), chatMessage.getMessage(), 
                userTypeFromSession, sessionUserId, session.getId());

        // 디버깅 로그: 발신자 설정 준비. 현재 chatMessage.sender: {}, 설정할 sessionUserId: {}, userTypeFromSession: {}
        log.debug("[WS_HandleMsg] 발신자 설정 준비. 현재 chatMessage.sender: {}, 설정할 sessionUserId: {}, userTypeFromSession: {}",
                  chatMessage.getSender(), sessionUserId, userTypeFromSession);

        // 1. 발신자(sender) 정보 설정
        //    - 관리자(admin)가 보낸 메시지의 경우, 클라이언트(adminchatview.js)에서 'sender' 필드를 '관리자'로 표시하기를 원하므로,
        //      여기서 직접 "관리자" 문자열을 설정합니다. 실제 관리자 식별자(sessionUserId, 예: "1")는 로그에만 기록됩니다.
        //    - 기업회원(company) 및 일반회원(member)의 경우, 기존대로 각자의 고유 ID(companyId 또는 memberEmail)를 발신자로 설정합니다.
        if ("admin".equals(userTypeFromSession)) {
            chatMessage.setSender("관리자");
            chatMessage.setSenderDisplayName("관리자"); // 관리자 표시 이름
            log.info("[WS_HandleMsg] 관리자 메시지: sender '관리자', senderDisplayName '관리자' 설정. (실제 adminId: {})", sessionUserId);
        } else if ("company".equals(userTypeFromSession)) {
            chatMessage.setSender(sessionUserId); // sender에는 companyId (sessionUserId) 저장
            String companyName = (String) session.getAttributes().get("companyName"); // 세션에서 companyName 가져오기
            if (companyName != null && !companyName.isEmpty()) {
                chatMessage.setSenderDisplayName(companyName); 
                log.info("[WS_HandleMsg] 기업회원 메시지: sender '{}', senderDisplayName '{}' 설정.", sessionUserId, companyName);
            } else {
                // 세션에 companyName이 없는 비상 상황 처리 (userLogin 객체에서 다시 가져오기 시도)
                Object userLoginObj = session.getAttributes().get("userLogin");
                if (userLoginObj instanceof CompanyVo) {
                    companyName = ((CompanyVo) userLoginObj).getCompanyName();
                    chatMessage.setSenderDisplayName(companyName);
                    log.info("[WS_HandleMsg] 기업회원 메시지 (userLogin 백업): sender '{}', senderDisplayName '{}' 설정.", sessionUserId, companyName);
                } else {
                    chatMessage.setSenderDisplayName(sessionUserId); // 최후의 보루로 companyId 사용
                    log.warn("[WS_HandleMsg] 기업회원 메시지: 세션에서 companyName 조회 실패. senderDisplayName에 companyId '{}' 사용.", sessionUserId);
                }
            }
        } else if ("member".equals(userTypeFromSession)) {
            chatMessage.setSender(sessionUserId); // sender에는 memberEmail (sessionUserId) 저장
            // memberName 대신 sessionUserId (memberEmail)을 senderDisplayName으로 사용합니다.
            chatMessage.setSenderDisplayName(sessionUserId); 
            log.info("[WS_HandleMsg] 일반회원 메시지: sender '{}', senderDisplayName '{}'(으)로 설정 (memberEmail 사용).", sessionUserId, sessionUserId);
        } else {
            // 기타 사용자 타입 또는 알 수 없는 경우
            chatMessage.setSender(sessionUserId);
            chatMessage.setSenderDisplayName(sessionUserId); // 기본값으로 ID 사용
            log.warn("[WS_HandleMsg] 알 수 없는 사용자 타입 ('{}') 또는 처리되지 않은 타입. sender 및 senderDisplayName에 sessionUserId '{}' 사용.", userTypeFromSession, sessionUserId);
        }
        
        // 디버깅 로그: 최종적으로 설정된 chatMessage의 sender 필드 값, 사용자 타입, 원본 sessionUserId(식별자) 확인
        log.info("[WS_HandleMsg] 메시지 발신자 최종 설정됨. chatMessage.sender: '{}', chatMessage.senderDisplayName: '{}', userType: '{}', 원본 sessionUserId: '{}'",
                 chatMessage.getSender(), chatMessage.getSenderDisplayName(), userTypeFromSession, sessionUserId);

        // 2. 채팅방 ID(roomId) 설정 및 채팅방 생성/조회 (ENTER 메시지 처리)
		if (chatMessage.getRoomId() == null || chatMessage.getRoomId().isEmpty()) {
		    if (chatMessage.getType() == ChatMessageVo.MessageType.ENTER) {
		        if ("admin".equals(userTypeFromSession)) {
		            log.warn("[WS_HandleMsg] 관리자 ENTER 메시지에 roomId가 누락되었습니다. 클라이언트에서 선택된 방 ID를 보내야 합니다. 세션 ID: {}", session.getId());
		            sendErrorMessageToSession(session, "입장할 채팅방 ID가 없습니다. UI에서 방을 선택하세요.");
		            return;
		        } else if ("company".equals(userTypeFromSession)) {
		            // 기업회원은 자신의 ID(sessionUserId = companyId)를 roomId로 사용
		            ChatRoomVo room = chatService.companyCreateRoom(Integer.parseInt(sessionUserId)); 
		            chatMessage.setRoomId(room.getRoomId());
                    log.info("[WS_HandleMsg] 기업회원 ENTER: roomId가 없어 companyId '{}' 기반으로 채팅방 '{}' 설정/생성.", sessionUserId, chatMessage.getRoomId());
		        } else if ("member".equals(userTypeFromSession)) {
		            // 일반회원은 자신의 ID(sessionUserId = memberEmail)를 roomId로 사용
		            ChatRoomVo room = chatService.createRoom(sessionUserId); 
		            chatMessage.setRoomId(room.getRoomId());
                    log.info("[WS_HandleMsg] 일반회원 ENTER: roomId가 없어 memberEmail '{}' 기반으로 채팅방 '{}' 설정/생성.", sessionUserId, chatMessage.getRoomId());
		        } else {
		            log.error("[WS_HandleMsg] ENTER 메시지 처리 중 인식할 수 없는 사용자 타입. sessionUserType: {}, 세션 ID: {}", userTypeFromSession, session.getId());
		            sendErrorMessageAndClose(session, "사용자 유형을 확인할 수 없어 채팅방에 입장할 수 없습니다.", CloseStatus.POLICY_VIOLATION);
		            return;
		        }
		        log.info("[WS_HandleMsg] ENTER 메시지로 채팅방 ID 자동 설정/생성됨: {}, 세션 ID: {}", chatMessage.getRoomId(), session.getId());
		    } else { // TALK 또는 다른 타입 메시지인데 roomId가 없는 경우: 오류
		        log.warn("[WS_HandleMsg] {} 타입 메시지에 roomId가 누락되었습니다. 발신자: {}, 내용: {}, 세션 ID: {}", 
		                 chatMessage.getType(), chatMessage.getSender(), chatMessage.getMessage(), session.getId());
		        sendErrorMessageToSession(session, "메시지 전송 실패: 채팅방 정보(roomId)가 누락되었습니다.");
		        return;
		    }
		}
        // 이제 chatMessage.getRoomId()는 (거의) 항상 값을 가짐

        log.debug("[WS_HandleMsg] findRoomById 호출 예정. targetRoomId={}", chatMessage.getRoomId());
		ChatRoomVo room = chatService.findRoomById(chatMessage.getRoomId());

        log.debug("[WS_HandleMsg] findRoomById 결과: room is null? {}, room.roomId={}, sessions.size()={}", 
                (room == null), (room != null ? room.getRoomId() : "N/A"), (room != null ? room.getSessions().size() : "N/A"));

		if (room == null) {
			log.warn("[WS_HandleMsg] ID '{}'에 해당하는 채팅방을 찾을 수 없습니다. (ENTER시 생성되었어야 함). 발신자: {}, 내용: {}, 세션 ID: {}", 
			         chatMessage.getRoomId(), chatMessage.getSender(), chatMessage.getMessage(), session.getId());
            sendErrorMessageToSession(session, "메시지를 보낼 채팅방("+chatMessage.getRoomId()+")을 찾을 수 없거나, 아직 생성되지 않았습니다.");
            return;
		}

        // ENTER 메시지일 경우, 현재 세션을 해당 ChatRoomVo의 sessions Set에 추가
        if (chatMessage.getType() == ChatMessageVo.MessageType.ENTER) {
            // WebSocketSession의 equals/hashCode는 기본적으로 객체 주소 비교이므로, 동일 세션 객체면 잘 동작함
            if (room.getSessions().add(session)) { // add()는 Set에 실제로 추가된 경우 true 반환
                log.info("[WS_HandleMsg] 세션 {}을 채팅방 '{}'의 세션 목록에 추가. 현재 세션 수: {}", session.getId(), room.getRoomId(), room.getSessions().size());
            } else {
                log.debug("[WS_HandleMsg] 세션 {}은 이미 채팅방 '{}'에 존재하여 추가하지 않음.", session.getId(), room.getRoomId());
            }
        }

		log.debug("[WS_HandleMsg] room.handleActions 호출 예정. roomVo.roomId={}, currentSessionId={}, chatMessageSender={}, chatMessageType={}", 
                room.getRoomId(), session.getId(), chatMessage.getSender(), chatMessage.getType());
		
        // 중요: room.handleActions 호출 전 로그. DB 중복 저장 문제가 지속될 경우, 이 로그를 통해
        // ChatRoomVo.handleActions 메소드가 동일 메시지에 대해 여러 번 호출되는지,
        // 또는 단일 호출 내에서 중복 처리가 발생하는지 확인해야 합니다.
        // WebSocketHandler는 각 수신 메시지에 대해 이 메소드를 한 번만 호출합니다.
        log.info("[WS_HandleMsg] ===> room.handleActions 호출 직전! roomId: {}, sender: '{}', senderDisplayName: '{}', type: {}, msg: '{}'. DB 중복 저장 시 이 호출이 중복되는지 또는 handleActions 내부 문제인지 확인 필요.",
                room.getRoomId(), chatMessage.getSender(), chatMessage.getSenderDisplayName(), chatMessage.getType(), chatMessage.getMessage());
		room.handleActions(session, chatMessage, chatService);
        log.debug("[WS_HandleMsg] room.handleActions 호출 완료. roomVo.roomId={}", room.getRoomId());
	}

	/**
	 * 웹소켓 연결이 종료된 후 호출되는 메소드입니다.
	 * 세션 활동 기록 및 활성 세션 목록에서 해당 세션을 제거합니다.
	 *
	 * @param session 종료된 웹소켓 세션
	 * @param status 종료 상태 정보
	 * @throws Exception 예외 발생 시
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		log.info("[WebSocketHandler] 웹소켓 연결 종료. 세션 ID: {}, 상태: {}", session.getId(), status);
		activeSessions.remove(session.getId());
		sessionLastActivityMap.remove(session.getId());

        // 모든 ChatRoomVo를 순회하며 해당 세션을 제거 (비효율적일 수 있음. 개선 필요)
        // TODO: 세션이 어떤 채팅방에 있었는지 정보를 어딘가에 저장해두고, 해당 채팅방에서만 제거하는 것이 효율적입니다.
        // 예를 들어, WebSocketSession의 attributes에 현재 참여중인 roomId를 저장해두는 방법.
        String userType = (String) session.getAttributes().get("userType");
        String closedSessionId = session.getId();

        chatService.findAllRoom(null).forEach(roomVo -> { // 임시로 adminId null로 전체 방 조회
            boolean removed = roomVo.getSessions().removeIf(s -> s.getId().equals(closedSessionId));
            if (removed) {
                log.info("[WebSocketHandler] 채팅방 '{}'에서 종료된 세션 {} 제거 완료.", roomVo.getRoomId(), closedSessionId);
                // 필요하다면, 방에 아무도 남지 않았을 때 추가 처리 (예: 방 비활성화)
                // 또는 다른 참여자에게 퇴장 알림 메시지 전송
                if ("admin".equals(userType)) {
                    // 관리자 퇴장 시 특별한 알림은 현재 없음
                } else {
                    // 사용자 퇴장 시 다른 참여자에게 알림 (선택적)
                    // ChatMessageVo leaveMessage = new ChatMessageVo(ChatMessageVo.MessageType.TALK, roomVo.getRoomId(), "System", session.getAttributes().get("userLogin").toString() + " 님이 퇴장했습니다.", new Date());
                    // roomVo.sendMessage(leaveMessage, chatService);
                }
            }
        });
	}

    /**
     * 전송 에러 발생 시 호출되는 메소드입니다.
     * 에러를 로깅합니다.
     *
     * @param session   에러가 발생한 세션
     * @param exception 발생한 예외
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        log.error("[WebSocketHandler] 웹소켓 전송 에러 발생. 세션 ID: {}, 에러: {}", session.getId(), exception.getMessage(), exception);
    }

	/**
	 * 특정 웹소켓 세션의 마지막 활동 시간을 현재 시간으로 업데이트합니다.
	 *
	 * @param session 활동 시간을 업데이트할 웹소켓 세션
	 */
	private void updateSessionActivity(WebSocketSession session) {
	    if (session != null && session.isOpen()) {
	        sessionLastActivityMap.put(session.getId(), System.currentTimeMillis());
	        log.trace("[WebSocketHandler] 세션 활동 시간 업데이트됨. 세션 ID: {}", session.getId());
        }
	}

	/**
	 * 비활성 세션 검사 스케줄러를 시작합니다 (필요한 경우, 즉 스케줄러가 종료되었거나 아직 시작되지 않은 경우).
	 * 이미 실행 중인 경우 추가로 시작하지 않습니다.
	 */
	private synchronized void startInactivityCheckerIfNeeded() {
		if (!inactivityCheckerScheduled) { 
			scheduler.scheduleAtFixedRate(() -> {
		        long currentTime = System.currentTimeMillis();
		        sessionLastActivityMap.forEach((sessionId, lastActivity) -> {
		            if (currentTime - lastActivity > INACTIVITY_TIMEOUT) {
		                log.info("[WebSocketHandler] 비활성 세션 감지. 세션 ID: {}. 연결을 종료합니다.", sessionId);
		                WebSocketSession inactiveSession = activeSessions.get(sessionId); 
		                if (inactiveSession != null && inactiveSession.isOpen()) {
		                    try {
		                        inactiveSession.close(CloseStatus.GOING_AWAY.withReason("Session inactive"));
		                    } catch (IOException e) {
		                        log.error("[WebSocketHandler] 비활성 세션 종료 중 오류 발생. 세션 ID: {}, 에러: {}", sessionId, e.getMessage(), e);
		                    }
		                }
		            }
		        });
			}, INACTIVITY_TIMEOUT, INACTIVITY_TIMEOUT, TimeUnit.MILLISECONDS);
			inactivityCheckerScheduled = true; 
			log.info("[WebSocketHandler] 비활성 세션 검사 스케줄러 시작됨. 검사 주기: {} ms", INACTIVITY_TIMEOUT);
		} else {
            log.debug("[WebSocketHandler] 비활성 검사 스케줄러는 이미 실행 중입니다.");
        }
	}

    /**
     * 특정 세션에 에러 메시지를 전송합니다.
     * @param session 메시지를 받을 세션
     * @param errorMessage 보낼 에러 메시지
     */
    private void sendErrorMessageToSession(WebSocketSession session, String errorMessage) {
        if (session != null && session.isOpen()) {
            try {
                log.warn("[WebSocketHandler] 에러 메시지 전송. 세션 ID: {}, 메시지: {}", session.getId(), errorMessage);
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(Map.of("error", errorMessage))));
            } catch (IOException e) {
                log.error("[WebSocketHandler] 에러 메시지 전송 실패. 세션 ID: {}, 에러: {}", session.getId(), e.getMessage(), e);
            }
        }
    }

    /**
     * 특정 세션에 에러 메시지를 전송하고 세션을 지정된 상태로 닫습니다.
     * @param session 메시지를 받고 닫힐 세션
     * @param errorMessage 보낼 에러 메시지
     * @param closeStatus 세션을 닫을 때 사용할 상태 코드
     */
    private void sendErrorMessageAndClose(WebSocketSession session, String errorMessage, CloseStatus closeStatus) {
        sendErrorMessageToSession(session, errorMessage);
        if (session != null && session.isOpen()) {
            try {
                session.close(closeStatus);
            } catch (IOException e) {
                log.error("[WebSocketHandler] 에러 메시지 전송 후 세션 종료 중 오류 발생. 세션 ID: {}, 에러: {}", session.getId(), e.getMessage(), e);
            }
        }
    }

	@PreDestroy 
	public void destroy() {
	    log.info("[WebSocketHandler] 스케줄러 종료 시도...");
	    scheduler.shutdown();
	    try {
	        if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
	            scheduler.shutdownNow();
	            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
	                log.error("[WebSocketHandler] 스케줄러가 정상적으로 종료되지 않았습니다.");
                }
	        }
	    } catch (InterruptedException ie) {
	        scheduler.shutdownNow();
	        Thread.currentThread().interrupt();
	    }
	    log.info("[WebSocketHandler] 스케줄러 종료 완료.");
	}
}
