package com.joblessfriend.jobfinder.recruitment.service;

import com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao;
import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.skill.dao.SkillDao;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.util.SearchVo;
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
    public int getRecruitmentTotalCount(SearchVo searchVo) {
        return recruitmentDao.getRecruitmentTotalCount(searchVo);
    }

    @Override
    public List<RecruitmentVo> recruitmentList(SearchVo searchVo) {
        return recruitmentDao.recruitmentList(searchVo);
    }
    @Override
    public RecruitmentVo getRecruitmentId(int jobPostId) {
        return recruitmentDao.getRecruitmentId(jobPostId);
    }
    @Override
    public List<WelfareVo> selectWelfareByJobPostId(int jobPostId) {
        return recruitmentDao.selectWelfareByJobPostId(jobPostId);
    }



	@Override
	@Transactional
	public void jobPostDelete(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		
		recruitmentDao.jobPostFileDelete(jobPostIdList);
		recruitmentDao.jobPostTagDelete(jobPostIdList);
		recruitmentDao.jobPostDelete(jobPostIdList);
		
	}




    @Transactional
    @Override
    public void insertRecruitment(RecruitmentVo recruitmentVo, List<Integer> tagIdList, List<WelfareVo> welfareList) {
        // 1. 메인 채용공고 insert → selectKey로 jobPostId 생성됨
        System.out.println("🚀 service 들어옴");
        recruitmentDao.insertRecruitment(recruitmentVo);


        // 2. 태그 연결
        recruitmentDao.insertJobPostTag(recruitmentVo, tagIdList);

        // 3. 복리후생 jobPostId 세팅
        int jobPostId = recruitmentVo.getJobPostId();
        for (WelfareVo vo : welfareList) {
            vo.setJobPostId(jobPostId);
            recruitmentDao.insertJobPostWelfare(vo); // 단건 삽입
        }
        if (recruitmentVo.getTempKey() != null && !recruitmentVo.getTempKey().isBlank()) {
            recruitmentDao.updateJobPostIdByTempKey( recruitmentVo.getJobPostId(), recruitmentVo.getTempKey());
        }
    }
    @Override
    public void insertJobPostFile(JobPostFileVo fileVo) {
        recruitmentDao.insertJobPostFile(fileVo);
    }
    @Override
    public void updateJobPostIdByTempKey(int jobPostId,String tempKey) {
        recruitmentDao.updateJobPostIdByTempKey(jobPostId ,tempKey);
    }

    @Override
    public int countFilteredPosts(FilterRequestVo filterRequestVo) {
        return recruitmentDao.countFilteredPosts(filterRequestVo);
    }

    @Override
    public List<RecruitmentVo> getFilteredRecruitmentList(FilterRequestVo filterRequestVo) {
        return recruitmentDao.getFilteredRecruitmentList(filterRequestVo);
    }
    @Override
    public int getFilteredRecruitmentTotalCount(FilterRequestVo filterRequestVo) {
        return recruitmentDao.getFilteredRecruitmentTotalCount(filterRequestVo);
    }
    //카운트필터

	@Override
	public boolean checkCompanyOwnsJobPost(int companyId, int jobPostId) {
	    RecruitmentVo recruitment = getRecruitmentId(jobPostId);
	    return recruitment != null && recruitment.getCompanyId() == companyId;
	}





}
