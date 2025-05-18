package com.joblessfriend.jobfinder.recruitment.service;

import com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao;
import com.joblessfriend.jobfinder.recruitment.domain.CompanyRecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.domain.FilterRequestVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.skill.dao.SkillDao;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RecruitmentServiceImpl implements RecruitmentService {

    @Autowired
    private RecruitmentDao recruitmentDao;

    @Autowired
    private SkillDao skillDao;


    public List<JobGroupVo> jobGroupList() {
        return recruitmentDao.jobGroupList();
    }

    @Override
    public List<JobGroupVo> jobList(int jobGroupId) {
        return recruitmentDao.jobList(jobGroupId);
    }

    @Override
    public List<RecruitmentVo> recruitmentList() {
        return recruitmentDao.recruitmentList();
    }

    @Override
    public RecruitmentVo getRecruitmentId(int jobPostId) {
        return recruitmentDao.getRecruitmentId(jobPostId);
    }

	@Override
	@Transactional
	public void jobPostDelete(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		
		recruitmentDao.jobPostFileDelete(jobPostIdList);
		recruitmentDao.jobPostTagDelete(jobPostIdList);
		recruitmentDao.jobPostDelete(jobPostIdList);
		
	}


    @Override
    @Transactional
    public void insertRecruitment(RecruitmentVo recruitmentVo, List<Integer> tagIdList) {
        recruitmentDao.insertRecruitment(recruitmentVo); // jobPostId 생성
        recruitmentDao.insertJobPostTag(recruitmentVo, tagIdList); // 생성된 ID 사용
    }




	@Override
	public List<RecruitmentVo> adminRecruitmentList() {
		// TODO Auto-generated method stub
		return recruitmentDao.adminRecruitmentList();
	}

	@Override
	public List<CompanyRecruitmentVo> companyRecruitmentSelectList(int companyId) {
		// TODO Auto-generated method stub
		return recruitmentDao.companyRecruitmentSelectList(companyId);
	}

    @Override
    public int countFilteredPosts(FilterRequestVo filterRequestVo) {
        return recruitmentDao.countFilteredPosts(filterRequestVo);
    }
    //카운트필터


}
