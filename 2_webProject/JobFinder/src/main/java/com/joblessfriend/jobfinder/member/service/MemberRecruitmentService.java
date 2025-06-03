package com.joblessfriend.jobfinder.member.service;

import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.member.domain.ApplyPostVo;
import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.util.SearchVo;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface MemberRecruitmentService {
   
	public List<RecruitmentVo> selectRecruitmentList(int memberId, SearchVo searchVo);
	
	public void deleteOne(int memberId, int jobPostId);

	public int bookmarkCount(int memberId, SearchVo searchVo);

	//찜 저장
	public void bookMarkInsertOne(int memberId, int jobPostId);

	public int applicationCount(int memberId, SearchVo searchVo);

	public List<ApplyPostVo> selectApplicationList(int memberId, SearchVo searchVo);

	
	

}
