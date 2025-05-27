package com.joblessfriend.jobfinder.member.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
import com.joblessfriend.jobfinder.chat.domain.UserChatRoomLastRead;
import com.joblessfriend.jobfinder.chat.repository.ChatMessageRepository;
import com.joblessfriend.jobfinder.chat.repository.UserChatRoomLastReadRepository;
import com.joblessfriend.jobfinder.chat.service.ChatService;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원 채팅 관련 HTTP 요청을 처리하는 컨트롤러 클래스입니다.
 * 채팅방 입장, 메시지 조회 등의 기능을 담당합니다.
 */
@Slf4j
@Controller
@RequestMapping("/member/chat")
@RequiredArgsConstructor
public class MemberChatController {

    private final ChatService chatService;
    private final ChatMessageRepository chatMessageRepository;
    private final UserChatRoomLastReadRepository userChatRoomLastReadRepository;

    /**
     * 회원의 채팅방으로 입장하거나, 채팅방이 없으면 생성하여 입장합니다.
     *
     * @param session 현재 HTTP 세션 객체. 회원 로그인 정보를 가져오는 데 사용됩니다.
     * @param model   View에 전달할 데이터를 담는 Model 객체. 채팅방 정보를 전달합니다.
     * @return 채팅방 화면의 경로를 반환합니다. 로그인하지 않은 경우 로그인 페이지로 리디렉션합니다.
     */
    @GetMapping("/room")
    public String enterOrCreateRoom(HttpSession session, Model model) {
        log.info("[MemberChatController] 채팅방 입장/생성 요청");
        
        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
        
        if (memberVo == null) {
            log.warn("[MemberChatController] 비로그인 사용자의 채팅방 접근 시도. 로그인 페이지로 리디렉션합니다.");
            return "redirect:/auth/login";
        }
        
        String roomId = memberVo.getEmail();
        ChatRoomVo room = chatService.createRoom(roomId);

        model.addAttribute("room", room);
        model.addAttribute("memberEmail", memberVo.getEmail());
        log.info("[MemberChatController] 회원 '{}'의 채팅방 '{}' 입장 처리 완료.", memberVo.getEmail(), room.getRoomId());
        return "member/chat/chatView";
    }

    /**
     * 특정 채팅방의 이전 메시지 내역을 조회합니다.
     *
     * @param roomId  메시지를 조회할 채팅방의 ID. URL 경로에서 가져옵니다.
     * @param session 현재 HTTP 세션 객체. 사용자 인증 및 ID 확인에 사용됩니다.
     * @return 성공 시 채팅 메시지 목록을 담은 ResponseEntity, 실패 시 에러 메시지를 담은 ResponseEntity를 반환합니다.
     */
    @GetMapping("/messages/{roomId}")
    @ResponseBody
    public ResponseEntity<?> getChatMessages(@PathVariable String roomId, HttpSession session) {
        log.info("[MemberChatController] 채팅방 '{}'의 메시지 내역 요청", roomId);

        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");

        if (memberVo == null) {
            log.warn("[MemberChatController] 비로그인 사용자의 메시지 조회 시도. 채팅방 ID: {}", roomId);
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        if (!memberVo.getEmail().equals(roomId)) {
            log.warn("[MemberChatController] 권한 없는 메시지 조회 시도. 사용자: {}, 요청된 방 ID: {}", memberVo.getEmail(), roomId);
            return ResponseEntity.status(403).body("접근 권한이 없습니다.");
        }
        
        try {
            List<ChatMessageVo> messages = chatService.getMessagesByRoomId(roomId, memberVo.getEmail());
            log.info("[MemberChatController] 채팅방 '{}'의 메시지 {}건 조회 완료.", roomId, messages.size());
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            log.error("[MemberChatController] 채팅방 '{}' 메시지 조회 중 오류 발생", roomId, e);
            return ResponseEntity.internalServerError().body("메시지 조회 중 오류가 발생했습니다.");
        }
    }

    /**
     * 회원의 안읽은 메시지 수를 확인합니다.
     * 회원은 자신의 이메일을 roomId로 하는 채팅방에서만 안읽은 메시지를 확인할 수 있습니다.
     *
     * @param session 현재 HTTP 세션 객체. 회원 로그인 정보를 가져오는 데 사용됩니다.
     * @return 안읽은 메시지 수를 담은 ResponseEntity를 반환합니다.
     */
    @GetMapping("/unread-count")
    @ResponseBody
    public ResponseEntity<?> getUnreadMessageCount(HttpSession session) {
        log.debug("[MemberChatController] 안읽은 메시지 수 확인 요청");

        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");

        if (memberVo == null) {
            log.warn("[MemberChatController] 비로그인 사용자의 안읽은 메시지 수 확인 시도");
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        try {
            String roomId = memberVo.getEmail(); // 회원의 채팅방 ID는 이메일과 동일
            String userId = memberVo.getEmail(); // 사용자 ID도 이메일

            // 마지막 읽은 시간 조회
            Optional<UserChatRoomLastRead> lastReadOpt = userChatRoomLastReadRepository.findByUserIdAndRoomId(userId, roomId);
            Date lastReadTime = lastReadOpt.map(UserChatRoomLastRead::getLastReadTimestamp).orElse(new Date(0)); // 읽은 기록이 없으면 1970년 1월 1일

            // 마지막 읽은 시간 이후의 관리자 메시지 수 계산 (sender가 "관리자"인 메시지)
            long unreadCount = chatMessageRepository.countByRoomIdAndSenderNotAndSendTimeAfter(roomId, userId, lastReadTime);

            log.debug("[MemberChatController] 회원 '{}'의 안읽은 메시지 수: {}", memberVo.getEmail(), unreadCount);
            return ResponseEntity.ok().body(unreadCount);

        } catch (Exception e) {
            log.error("[MemberChatController] 안읽은 메시지 수 확인 중 오류 발생. 회원: {}", memberVo.getEmail(), e);
            return ResponseEntity.internalServerError().body("안읽은 메시지 수 확인 중 오류가 발생했습니다.");
        }
    }
}
