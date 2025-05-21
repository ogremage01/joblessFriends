package com.joblessfriend.jobfinder.member.service;

import com.joblessfriend.jobfinder.member.dao.MemberRecruitmentDao;
import com.joblessfriend.jobfinder.recruitment.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class MemberRecruitmentServiceImpl implements MemberRecruitmentService {

    @Autowired
    private MemberRecruitmentDao recruitmentDao;



	@Override
	public List<RecruitmentVo> selectRecruitmentList(int memberId) {
		// TODO Auto-generated method stub
		return recruitmentDao.selectRecruitmentList(memberId);
	}


}
