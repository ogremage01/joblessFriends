package com.joblessfriend.jobfinder.recruitment.dao;

import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RecruitmentDaoImpl implements RecruitmentDao {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<JobGroupVo> jobGroupList() {
        return sqlSession.selectList("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.jobGroupList");

    }

    @Override
    public List<JobGroupVo> jobList(int jobGroupId) {
        return sqlSession.selectList("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.jobList",jobGroupId);
    }

    @Override
    public List<RecruitmentVo> recruitmentList() {
        return sqlSession.selectList("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.RecruitmentList");
    }

    @Override
    public RecruitmentVo getRecruitmentId(int jobPostId) {
        return sqlSession.selectOne("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.getRecruitmentId",jobPostId);
    }

	@Override
	public int jobPostDelete(List<Integer> jobPostIdList) {
		return sqlSession.delete("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.jobPostDelete", jobPostIdList);
	}


    @Override
    public void insertRecruitment(RecruitmentVo recruitmentVo) {
       sqlSession.insert("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.insertRecruitment", recruitmentVo);
/*
        return recruitmentVo; // selectKey로 세팅된 ID가 들어 dlT*/
    }

    @Override
    public void insertJobPostTag(RecruitmentVo recruitmentVo, List<Integer> tagIdList) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("jobPostId", recruitmentVo.getJobPostId());
        paramMap.put("tagIdList", tagIdList);

        sqlSession.insert("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.insertJobPostTag", paramMap);
    }


}
