package com.joblessfriend.jobfinder.member.dao;

import com.joblessfriend.jobfinder.member.domain.MemberVo;

public interface MemberDao {

	MemberVo memberExist(String email, String password);

	int memberInsertOne(MemberVo memberVo);

	boolean isNicknameExists(String nickname);

	MemberVo memberEmailExist(String email);

}
