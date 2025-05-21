package com.joblessfriend.jobfinder.member.service;

import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.recruitment.domain.*;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface MemberRecruitmentService {
   
	public List<RecruitmentVo> selectRecruitmentList(int memberId);

}
