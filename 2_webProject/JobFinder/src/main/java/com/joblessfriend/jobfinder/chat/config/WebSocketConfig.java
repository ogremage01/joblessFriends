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
               .setAllowedOrigins("*")  // 모든 도메인 허용
               .withSockJS()
               .setWebSocketEnabled(true)
               .setDisconnectDelay(30 * 1000)
               .setHeartbeatTime(25 * 1000);
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

                    // 어드민 세션 확인
                    AdminVo admin = (AdminVo) session.getAttribute("admin");
                    if (admin != null) {
                        log.info("Admin WebSocket connection established");
                        attributes.put("userType", "admin");
                        attributes.put("userLogin", admin);
                        return true;
                    }
                    
                    // 기업회원 세션 확인
                    Object companyObj = session.getAttribute("userLogin");
                    if (companyObj != null && companyObj instanceof CompanyVo) {
                        CompanyVo company = (CompanyVo) companyObj;
                        log.info("Company WebSocket connection: {}", company.getCompanyName());
                        attributes.put("userType", "company");
                        attributes.put("companyId", company.getCompanyId());
                        attributes.put("companyName", company.getCompanyName());
                        attributes.put("userLogin", company);
                        return true;
                    }
                    
                    // 일반 사용자 세션 확인
                    Object userObj = session.getAttribute("userLogin");
                    if (userObj != null && userObj instanceof MemberVo) {
                        MemberVo member = (MemberVo) userObj;
                        log.info("Member WebSocket connection: {}", member.getEmail());
                        attributes.put("userLogin", member);
                        attributes.put("userType", "member");
                        return true;
                    }

                    log.warn("Invalid session type found");
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
