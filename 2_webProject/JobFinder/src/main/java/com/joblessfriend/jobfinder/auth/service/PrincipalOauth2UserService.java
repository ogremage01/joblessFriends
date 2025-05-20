package com.joblessfriend.jobfinder.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.auth.domain.PrincipalDetails;
import com.joblessfriend.jobfinder.member.dao.MemberDao;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.member.service.MemberService;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	
	private final PasswordEncoder passwordEncoder =
		new BCryptPasswordEncoder();
	
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberService memberService;
	
	public OAuth2User loadUser(OAuth2UserRequest userRequest) 
		throws OAuth2AuthenticationException {
		
		//🔑 토큰과 사용자 정보 확인
		System.out.println("구글 정보 확인: " 
				+ userRequest.getAccessToken().getTokenValue());
		System.out.println("구글 정보 확인: " 
				+ super.loadUser(userRequest).getAttributes());
		
		//📦 사용자 정보 추출
		OAuth2User oAuth2User = super.loadUser(userRequest);
		String email = oAuth2User.getAttribute("email");
		String password = "{noop}oauth2-user";
		String provider = userRequest.getClientRegistration().getClientId();
		
		//DB에 이미 이 이메일을 가진 회원이 있는지 확인
		MemberVo memberVo = memberDao.googleMemberExist(email);
		if (memberVo == null) {
			// 가입
			System.out.println("최초 구글 로그인 / 가입");
			
			memberVo = new MemberVo();
			memberVo.setEmail(email);
			memberVo.setPassword(password);
			memberVo.setProvider(provider);
			
			//닉네임 생성
			memberVo.setNickname(memberService.generateUniqueNickname());
			memberVo.setProvider("google");
			
			memberDao.memberInsertOne(memberVo);
		} else {
			//로그인
			System.out.println("이미 존재하는 회원입니다.");
		}
		
		//Spring Security가 이 리턴 값을 받아서 세션에 사용자로 등록
		return new PrincipalDetails(memberVo, oAuth2User.getAttributes());
	}
}
