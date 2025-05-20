package com.joblessfriend.jobfinder.auth.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.joblessfriend.jobfinder.member.domain.MemberVo;

// Oauth2 로그인 커스텀 마이징
public class PrincipalDetails implements UserDetails, OAuth2User{

	private MemberVo memberVo;
	private Map<String, Object> attributes;
	
	public PrincipalDetails(MemberVo memberVo) {
		this.memberVo = memberVo;
	}
	
	public PrincipalDetails(MemberVo memberVo, Map<String, Object> attributes) {
		this.memberVo = memberVo;
		this.attributes = attributes;
	}
	
	@Override // OAuth2 로그인 시 받은 사용자 정보들 반환 (ex. 구글 계정 정보)
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}
	
	@Override // OAuth2 로그인에서 "이메일"을 가져옴 (attributes에서 "email" 키 값)
	public String getName() {
		// TODO Auto-generated method stub
		return (String)attributes.get("email");
	}
	@Override // 사용자 권한 정보를 반환
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return "ROLE_USER";
			}
		});
		
		return collect;
	}
	
	@Override // 비밀번호 반환 (일반 로그인에서 사용됨)
	public String getPassword() {
		// TODO Auto-generated method stub
		return memberVo.getPassword();
	}
	@Override // 사용자 이름 반환 (여기서는 email)
	public String getUsername() {
		// TODO Auto-generated method stub
		return memberVo.getEmail();
	}
	
	
	// 계정 상태 체크. 모두 true로 되어 있어, 계정 잠금 X, 만료 X, 항상 로그인 가능
	
	public boolean isAccountNonExpired() {
		return true;
	}
	
	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

	public MemberVo getMemberVo() {
		return memberVo;
	}
	
	
	
}
