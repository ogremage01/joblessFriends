package com.joblessfriend.jobfinder.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.company.dao.CompanyDao;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;



	@Override
	public CompanyVo companySelectOne(int companyId) {
		// TODO Auto-generated method stub
		return companyDao.companySelectOne(companyId);
	}

	@Override
	public int companyUpdateOne(CompanyVo existCompanyVo) {
		// TODO Auto-generated method stub
		return companyDao.companyUpdateOne(existCompanyVo);
		
	}


	@Override
	public int companyDeleteOne(int companyId) {
		// TODO Auto-generated method stub
		return companyDao.companyDeleteOne(companyId);
	}


	// 기업회원가입
	@Override
	public int companyInsertOne(CompanyVo companyVo) {
		
		String password = companyVo.getPassword();
		
		//비밀번호 암호화
		String pwdEncoder = passwordEncoder.encode(password);
		System.out.println("비번 확인: " + password + " / " + pwdEncoder);
		companyVo.setPassword(pwdEncoder);
		
		return companyDao.companyInsertOne(companyVo);
	}

	// 기업로그인
	@Override
	public CompanyVo companyExist(String email, String password) {
		// TODO Auto-generated method stub
		
		CompanyVo companyVo = companyDao.companyEmailExist(email);
		
		if(companyVo != null) {
			// 암호 비교: 비밀번호가 평문인 경우
			if(password.equals(companyVo.getPassword())) {
				return companyVo;
			}
			// 암호가 암호화된 경우, passwordEncoder 사용
			else if(passwordEncoder.matches(password, companyVo.getPassword())) {
				return companyVo;
			}
		}
		
		// 회원이 존재하지 않거나 비밀번호가 틀린 경우 null 반환
		return null;
	}
	
	// Id 찾기
	@Override
	public CompanyVo companyFindId(String representative, String brn) {
		// TODO Auto-generated method stub
		return companyDao.companyFindId(representative, brn);
	}

	// 이메일 중복확인
	@Override
	public CompanyVo companyEmailExist(String email) {
		// TODO Auto-generated method stub
		return companyDao.companyEmailExist(email);

	}
	
	// 비밀번호 변경
	@Override
	public int updatePassword(String password, int companyId) {
		// TODO Auto-generated method stub
		
		//비밀번호 암호화
		String pwdEncoder = passwordEncoder.encode(password);
		System.out.println("비번 확인: " + password + " / " + pwdEncoder);
		
		return companyDao.updatePassword(pwdEncoder, companyId);
	}

}
