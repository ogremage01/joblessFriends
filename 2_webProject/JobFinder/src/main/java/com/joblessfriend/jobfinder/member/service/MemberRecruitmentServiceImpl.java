package com.joblessfriend.jobfinder.member.service;

import com.joblessfriend.jobfinder.member.dao.MemberRecruitmentDao;
import com.joblessfriend.jobfinder.member.domain.ApplyPostVo;
import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.util.SearchVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class MemberRecruitmentServiceImpl implements MemberRecruitmentService {

    @Autowired
    private MemberRecruitmentDao recruitmentDao;



	@Override
	public List<RecruitmentVo> selectRecruitmentList(int memberId, SearchVo searchVo) {
		// TODO Auto-generated method stub
		return recruitmentDao.selectRecruitmentList(memberId, searchVo);
	}

	
	@Override
	public void deleteOne(int memberId, int jobPostId) {
		// TODO Auto-generated method stub

		recruitmentDao.deleteOne(memberId, jobPostId);
	}


	@Override
	public int bookmarkCount(int memberId, SearchVo searchVo) {
		// TODO Auto-generated method stub
		return recruitmentDao.bookmarkCount(memberId, searchVo);
	}

	//찜 저장
	@Override
	public void bookMarkInsertOne(int memberId, int jobPostId) {
		// TODO Auto-generated method stub
		recruitmentDao.bookMarkInsertOne(memberId, jobPostId);
	}


	@Override
	public int applicationCount(int memberId, SearchVo searchVo) {
		// TODO Auto-generated method stub
		return recruitmentDao.applicationCount(memberId, searchVo);
	}


	@Override
	public List<ApplyPostVo> selectApplicationList(int memberId, SearchVo searchVo) {
		// TODO Auto-generated method stub
		return recruitmentDao.selectApplicationList(memberId, searchVo);
	}

}
