package com.joblessfriend.jobfinder.config.intercepter;



import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.joblessfriend.jobfinder.admin.domain.AdminVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminLoginCheckIntercepter implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
        AdminVo admin = (AdminVo) request.getSession().getAttribute("userLogin"); // 세션 키 확인
        
        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login"); // 로그인 안됐으면 로그인 페이지로
            return false;
        }
        return true;
        
	}

}
