package com.joblessfriend.jobfinder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.joblessfriend.jobfinder.auth.domain.PrincipalDetails;
import com.joblessfriend.jobfinder.auth.service.PrincipalOauth2UserService;
import com.joblessfriend.jobfinder.member.dao.MemberDao;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.RequestDispatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private PrincipalOauth2UserService principalOAuth2UserService;

	@Autowired
	MemberDao memberDao;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.anyRequest().permitAll() //권한 상관없이 접근가능
				)

				// OAuth2 로그인 설정
				.oauth2Login(oauth2login -> oauth2login
					.loginPage("/auth/login") // 구글 로그인 버튼 누르면 이동할 로그인 페이지
					
					//구글에서 로그인 성공 후 사용자 정보를 가져올 때 **PrincipalOauth2UserService**를 사용하겠다는 뜻
					.userInfoEndpoint(userInfo -> userInfo.userService(principalOAuth2UserService))
					
					//로그인 성공 시
					.successHandler((request, response, authentication) -> {
						PrincipalDetails userDetails = (PrincipalDetails)authentication.getPrincipal();
						
						//정보 다시 조회해서 세션에 넣어줌
						MemberVo memberVo = userDetails.getMemberVo();
						memberVo = memberDao.memberEmailExist(memberVo.getEmail());

						// 세션 설정
						request.getSession().setAttribute("userLogin", memberVo);
					    request.getSession().setAttribute("userType", "member"); 
						
						response.sendRedirect("/");
					})
				) // oauth2Login end
				
				.build();
	} // securityFilterChain 종료
	
	
	
	
}
	

