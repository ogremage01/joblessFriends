package com.joblessfriend.jobfinder.member.dao;

import com.joblessfriend.jobfinder.recruitment.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberRecruitmentDaoImpl implements MemberRecruitmentDao {

    @Autowired
    private SqlSession sqlSession;

    String namespace = "com.joblessfriend.jobfinder.member.dao.RecruitmentDao";

    

	@Override
	public List<RecruitmentVo> selectRecruitmentList(int memberId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+".selectRecruitmentList",memberId);
	}
	

}
