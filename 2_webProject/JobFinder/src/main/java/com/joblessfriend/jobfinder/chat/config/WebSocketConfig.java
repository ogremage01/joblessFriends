package com.joblessfriend.jobfinder.chat.config;

import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.joblessfriend.jobfinder.admin.domain.AdminVo;
import com.joblessfriend.jobfinder.chat.handler.WebSocketHandler;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/chat")
               .addInterceptors(new CustomHandshakeInterceptor())
               .setAllowedOriginPatterns("http://*:*", "https://*:*")
               .withSockJS();
    }

    private class CustomHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                     org.springframework.web.socket.WebSocketHandler wsHandler,
                                     Map<String, Object> attributes) throws Exception {
            if (request instanceof ServletServerHttpRequest) {
                ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                HttpSession session = servletRequest.getServletRequest().getSession();
                
                // 어드민 세션 확인
                AdminVo admin = (AdminVo) session.getAttribute("admin");
                if (admin != null) {
                    attributes.put("userType", "admin");
                    return true;
                }
                
                // 기업회원 세션 확인
                CompanyVo company = (CompanyVo) session.getAttribute("companyLogin");
                if (company != null) {
                    attributes.put("userType", "company");
                    attributes.put("companyId", company.getCompanyId());
                    attributes.put("companyName", company.getCompanyName());
                    return true;
                }
                
                // 일반 사용자 세션 확인
                Object userLogin = session.getAttribute("userLogin");
                if (userLogin != null) {
                    attributes.put("userLogin", userLogin);
                    attributes.put("userType", "member");
                }
            }
            return super.beforeHandshake(request, response, wsHandler, attributes);
        }
    }
}
