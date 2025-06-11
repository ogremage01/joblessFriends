package com.joblessfriend.jobfinder.chat.config;

import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
// import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor; // 현재 커스텀 인터셉터 사용으로 주석 처리

// import com.joblessfriend.jobfinder.admin.domain.AdminVo; // CustomHandshakeInterceptor 내에서 직접 타입 캐스팅은 지양, Object로 처리
import com.joblessfriend.jobfinder.chat.handler.WebSocketHandler; // 사용자 정의 웹소켓 핸들러
// import com.joblessfriend.jobfinder.company.domain.CompanyVo; // CustomHandshakeInterceptor 내에서 직접 타입 캐스팅은 지양
// import com.joblessfriend.jobfinder.member.domain.MemberVo; // CustomHandshakeInterceptor 내에서 직접 타입 캐스팅은 지양

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 웹소켓 관련 설정을 구성하는 클래스입니다.
 * {@link WebSocketConfigurer} 인터페이스를 구현하여 웹소켓 핸들러, 인터셉터, SockJS 등을 설정합니다.
 */
@Slf4j // Lombok: SLF4J 로거를 자동으로 생성합니다.
@Configuration // 이 클래스가 Spring의 설정 클래스임을 나타냅니다.
@EnableWebSocket // Spring에서 웹소켓 기능을 활성화합니다.
@RequiredArgsConstructor // Lombok: final 필드에 대한 생성자를 자동으로 생성합니다.
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler; // 주입받은 사용자 정의 웹소켓 핸들러

    /**
     * 웹소켓 핸들러를 등록하고 설정을 구성합니다.
     *
     * @param registry 웹소켓 핸들러를 등록할 수 있는 {@link WebSocketHandlerRegistry}
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/chat") // "/ws/chat" 경로로 웹소켓 요청을 처리할 핸들러를 등록합니다.
               .addInterceptors(new CustomHandshakeInterceptor()) // 웹소켓 핸드셰이크 과정에 커스텀 인터셉터를 추가합니다.
               .setAllowedOriginPatterns("*")  // 모든 도메인에서의 웹소켓 연결 요청을 허용합니다. (보안상 주의, 실제 운영 환경에서는 특정 도메인 지정 권장)
               .withSockJS() // SockJS를 활성화하여 웹소켓을 지원하지 않는 브라우저에서도 유사한 기능을 제공합니다.
               // SockJS 클라이언트 라이브러리 URL을 지정합니다. 클라이언트 측에서 이 라이브러리를 사용하여 연결합니다.
               .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js")
               .setWebSocketEnabled(true); // 순수 웹소켓 프로토콜 사용을 활성화합니다.
        log.info("[WebSocketConfig] 웹소켓 핸들러 등록 완료. 경로: /ws/chat, SockJS 활성화됨.");
    }

    /**
     * 웹소켓 핸드셰이크 과정에 개입하여 HTTP 세션 정보를 웹소켓 세션 속성으로 전달하는 커스텀 인터셉터 클래스입니다.
     * 이를 통해 웹소켓 핸들러에서 사용자 인증 정보 등을 활용할 수 있게 됩니다.
     */
    private static class CustomHandshakeInterceptor implements HandshakeInterceptor {
        /**
         * 핸드셰이크 요청을 처리하기 전에 호출됩니다.
         * HTTP 세션에서 "userType"과 "userLogin" 속성을 읽어 웹소켓 세션 속성(attributes)에 저장합니다.
         * 세션 정보가 없거나 유효하지 않으면 핸드셰이크를 중단하고 연결을 허용하지 않습니다.
         *
         * @param request 현재 HTTP 요청
         * @param response 현재 HTTP 응답
         * @param wsHandler 요청을 처리할 웹소켓 핸들러
         * @param attributes 웹소켓 세션에 전달될 속성 맵 (이 맵에 데이터를 추가하면 핸들러에서 사용 가능)
         * @return 핸드셰이크를 계속 진행할지(true), 중단할지(false) 여부
         * @throws Exception 예외 발생 시
         */
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                     org.springframework.web.socket.WebSocketHandler wsHandler,
                                     Map<String, Object> attributes) throws Exception {
            log.debug("[CustomHandshakeInterceptor] beforeHandshake 시작. 요청 URI: {}", request.getURI());
            try {
                if (request instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                    // HTTP 세션을 가져옵니다. 세션이 없으면 생성하지 않고 null을 반환합니다 (false 인수).
                    HttpSession httpSession = servletRequest.getServletRequest().getSession(false);
                    
                    if (httpSession == null) {
                        log.warn("[CustomHandshakeInterceptor] 웹소켓 연결 시 HTTP 세션을 찾을 수 없습니다. URI: {}", request.getURI());
                        return false; // 세션이 없으면 핸드셰이크 중단
                    }

                    // HTTP 세션에서 사용자 타입과 로그인 정보를 가져옵니다.
                    String userType = (String) httpSession.getAttribute("userType");
                    Object userLogin = httpSession.getAttribute("userLogin"); // AdminVo, MemberVo, CompanyVo 등이 될 수 있음

                    if (userType == null || userLogin == null) {
                        log.warn("[CustomHandshakeInterceptor] HTTP 세션에 userType 또는 userLogin 정보가 없습니다. userType: {}, userLogin is null: {}. URI: {}", 
                                 userType, (userLogin == null), request.getURI());
                        return false; // 필수 정보가 없으면 핸드셰이크 중단
                    }

                    // 사용자 타입에 따라 로그를 남기고 웹소켓 세션 속성에 정보를 저장합니다.
                    // (주의: userLogin 객체의 실제 타입 검증은 WebSocketHandler에서 수행하는 것이 더 안전할 수 있습니다.)
                    if ("admin".equals(userType) || "member".equals(userType) || "company".equals(userType)) {
                        log.info("[CustomHandshakeInterceptor] {} 타입 사용자 웹소켓 핸드셰이크 승인. 세션 ID: {}, URI: {}", 
                                 userType, httpSession.getId(), request.getURI());
                        attributes.put("userType", userType); // 웹소켓 세션에 사용자 타입 저장
                        attributes.put("userLogin", userLogin); // 웹소켓 세션에 로그인 정보 객체 저장
                        return true; // 핸드셰이크 계속 진행
                    } else {
                        log.warn("[CustomHandshakeInterceptor] 유효하지 않은 사용자 타입입니다: {}. 세션 ID: {}, URI: {}", 
                                 userType, httpSession.getId(), request.getURI());
                        return false; // 유효하지 않은 사용자 타입이면 핸드셰이크 중단
                    }
                } else {
                    log.warn("[CustomHandshakeInterceptor] 웹소켓 핸드셰이크 요청이 ServletServerHttpRequest 타입이 아닙니다. 요청 타입: {}", request.getClass().getName());
                    return false; // 서블릿 요청이 아니면 핸드셰이크 중단 (일반적으로 웹 환경에서는 발생하기 어려움)
                }
            } catch (Exception e) {
                log.error("[CustomHandshakeInterceptor] 웹소켓 핸드셰이크 중 오류 발생. URI: {}. 에러: {}", request.getURI(), e.getMessage(), e);
                return false; // 예외 발생 시 핸드셰이크 중단
            }
        }

        /**
         * 핸드셰이크가 완료된 후 호출됩니다 (성공/실패 여부와 관계없이).
         * 핸드셰이크 과정에서 발생한 예외를 로깅하는 데 사용할 수 있습니다.
         *
         * @param request 현재 HTTP 요청
         * @param response 현재 HTTP 응답
         * @param wsHandler 요청을 처리한 웹소켓 핸들러
         * @param ex 핸드셰이크 중에 발생한 예외 (없으면 null)
         */
        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                 org.springframework.web.socket.WebSocketHandler wsHandler,
                                 Exception ex) {
            if (ex == null) {
                log.debug("[CustomHandshakeInterceptor] afterHandshake 성공. 요청 URI: {}", request.getURI());
            } else {
                log.error("[CustomHandshakeInterceptor] 웹소켓 핸드셰이크 후 오류 발생. 요청 URI: {}. 에러: {}", request.getURI(), ex.getMessage(), ex);
            }
        }
    }
}
