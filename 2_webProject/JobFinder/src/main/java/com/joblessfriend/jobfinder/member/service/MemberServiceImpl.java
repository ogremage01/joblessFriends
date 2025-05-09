package com.joblessfriend.jobfinder.member.service;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.auth.controller.AuthController;
import com.joblessfriend.jobfinder.member.dao.MemberDao;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

@Service
public class MemberServiceImpl implements MemberService{
	
	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private MemberDao memberDao;
	
	// 로그인
	@Override
	public MemberVo memberExist(String email, String password) {
		// TODO Auto-generated method stub

		MemberVo memberVo = memberDao.memberExist(email, password);
		
		return memberVo;
	}
	
	// 닉네임 생성 메소드
	public String generateUniqueNickname() {
		final String CHAR_POOL = "abcdefghijklmnopqrstuvwxyz0123456789";
		final int LENGTH = 6;
		SecureRandom random = new SecureRandom();
		String nickname;

		do {
			StringBuilder sb = new StringBuilder("user");
			for (int i = 0; i < LENGTH; i++) {
				int index = random.nextInt(CHAR_POOL.length());
				sb.append(CHAR_POOL.charAt(index));
			}
			nickname = sb.toString();
		} while (memberDao.isNicknameExists(nickname));  // 중복 확인

		return nickname;
	}
	
	// 회원가입
	@Override
	public int memberInsertOne(String email, String password) {
		MemberVo memberVo = new MemberVo();
		memberVo.setEmail(email);
		memberVo.setPassword(password);
		memberVo.setNickname(generateUniqueNickname());
		return memberDao.memberInsertOne(memberVo);
	}

}
