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
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.joblessfriend.jobfinder.admin.domain.AdminVo;
import com.joblessfriend.jobfinder.chat.handler.WebSocketHandler;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/chat")
               .addInterceptors(new CustomHandshakeInterceptor())
               .setAllowedOriginPatterns("*")  // 모든 도메인 허용
               .withSockJS()
               .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.1/sockjs.min.js")
               .setWebSocketEnabled(true);
    }

    private class CustomHandshakeInterceptor implements HandshakeInterceptor {
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                     org.springframework.web.socket.WebSocketHandler wsHandler,
                                     Map<String, Object> attributes) throws Exception {
            try {
                if (request instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                    HttpSession session = servletRequest.getServletRequest().getSession(false);
                    
                    if (session == null) {
                        log.warn("No session found for WebSocket connection");
                        return false;
                    }

                    // 사용자 타입 확인
                    String userType = (String) session.getAttribute("userType");
                    Object userLogin = session.getAttribute("userLogin");

                    if (userLogin == null) {
                        log.warn("No user login found in session");
                        return false;
                    }

                    if ("admin".equals(userType)) {
                        log.info("Admin WebSocket connection established");
                        attributes.put("userType", "admin");
                        attributes.put("userLogin", userLogin);
                        return true;
                    } else if ("member".equals(userType)) {
                        log.info("Member WebSocket connection established");
                        attributes.put("userType", "member");
                        attributes.put("userLogin", userLogin);
                        return true;
                    } else if ("company".equals(userType)) {
                        log.info("Company WebSocket connection established");
                        attributes.put("userType", "company");
                        attributes.put("userLogin", userLogin);
                        return true;
                    }
                    
                    log.warn("Invalid user type: {}", userType);
                    return false;
                }
                log.warn("Request is not a ServletServerHttpRequest");
                return false;
            } catch (Exception e) {
                log.error("WebSocket handshake failed", e);
                return false;
            }
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                 org.springframework.web.socket.WebSocketHandler wsHandler,
                                 Exception ex) {
            if (ex != null) {
                log.error("Error after WebSocket handshake", ex);
            }
        }
    }
}
