package com.joblessfriend.jobfinder.recruitment.dao;

import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.util.SearchVo;
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

    String namespace = "com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao";

    @Override
    public List<JobGroupVo> jobGroupList() {
        return sqlSession.selectList(namespace+".jobGroupList");

    }

    @Override
    public List<JobGroupVo> jobList(int jobGroupId) {
        return sqlSession.selectList(namespace+".jobList",jobGroupId);
    }

    @Override
    public int getRecruitmentTotalCount(SearchVo searchVo) {
        return sqlSession.selectOne(namespace + ".getRecruitmentTotalCount", searchVo);
    }

    @Override
    public List<RecruitmentVo> recruitmentList(SearchVo searchVo) {
        return sqlSession.selectList(namespace + ".RecruitmentList", searchVo);
    }
    @Override
    public RecruitmentVo getRecruitmentId(int jobPostId) {
        return sqlSession.selectOne(namespace+".getRecruitmentId",jobPostId);
    }

    @Override
    public List<WelfareVo> selectWelfareByJobPostId(int jobPostId) {
        return sqlSession.selectList(namespace+".selectWelfareByJobPostId",jobPostId);
    }

    @Override
	public void jobPostDelete(List<Integer> jobPostIdList) {
		sqlSession.delete(namespace+".jobPostDelete", jobPostIdList);
	}



    @Override
    public void insertRecruitment(RecruitmentVo recruitmentVo) {
        System.out.println("🔥 insertRecruitment 에서 실제 할당된 jobPostId: " + recruitmentVo.getJobPostId());
       sqlSession.insert(namespace+".insertRecruitment", recruitmentVo);
/*
        return recruitmentVo; // selectKey로 세팅된 ID가 들어 dlT*/
    }

    @Override
    public void insertJobPostTag(RecruitmentVo recruitmentVo, List<Integer> tagIdList) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("jobPostId", recruitmentVo.getJobPostId());
        paramMap.put("tagIdList", tagIdList);

        sqlSession.insert(namespace+".insertJobPostTag", paramMap);
    }
    @Override
    public void insertJobPostWelfare(WelfareVo Welfarevo) {

        sqlSession.insert(namespace + ".insertJobPostWelfare", Welfarevo);
    }
    @Override
    public void insertJobPostFile(JobPostFileVo fileVo) {
        sqlSession.insert(namespace+".insertJobPostFile", fileVo);
    }

    @Override
    public void insertQuestion(JobPostQuestionVo questionVo) {
        sqlSession.insert(namespace+".insertQuestion", questionVo);
    }

    @Override
    public void updateJobPostIdByTempKey(int jobPostId,String tempKey) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tempKey", tempKey);
        paramMap.put("jobPostId", jobPostId);

        sqlSession.update(namespace + ".updateJobPostIdByTempKey", paramMap);
    }

    @Override
    public void updateRecruitment(RecruitmentVo recruitmentVo) {
        sqlSession.update(namespace+".updateRecruitment", recruitmentVo);
    }
    @Override
    public List<JobPostQuestionVo> getRecruitmentQuestion(int jobPostId) {
        return sqlSession.selectList(namespace+".getRecruitmentQuestion" ,jobPostId);
    }
    @Override
    public void deleteTagsByJobPostId(int jobPostId) {
        sqlSession.delete(namespace+".deleteTagsByJobPostId", jobPostId);
    }

    @Override
    public void deleteWelfareByJobPostId(int jobPostId) {
        sqlSession.delete(namespace+".deleteWelfareByJobPostId",jobPostId);
    }

    @Override
    public void increaseViews(int jobPostId) {
        sqlSession.update(namespace+".increaseViews", jobPostId);
    }

    @Override
    public void deleteQuestionsByJobPostId(int jobPostId) {
        sqlSession.delete(namespace+".deleteQuestionsByJobPostId", jobPostId);
    }

    @Override
    public void deleteAnswersByJobPostId(int jobPostId) {
        sqlSession.delete(namespace+".deleteAnswersByJobPostId", jobPostId);
    }

    @Override
    public void updateQuestionTextByOrder(JobPostQuestionVo questionVo) {
        sqlSession.update(namespace+".updateQuestionTextByOrder", questionVo);
    }


    //필터카운팅
    @Override
    public int countFilteredPosts(FilterRequestVo filterRequestVo) {
        return sqlSession.selectOne(namespace+".countFilteredPosts", filterRequestVo);
    }

    @Override
    public List<RecruitmentVo> getFilteredRecruitmentList(FilterRequestVo filterRequestVo) {
        return sqlSession.selectList(namespace+".getFilteredRecruitmentList",filterRequestVo);
    }
    @Override
    public int getFilteredRecruitmentTotalCount(FilterRequestVo filterRequestVo) {
        return sqlSession.selectOne(namespace + ".getFilteredRecruitmentTotalCount", filterRequestVo);
    }

    @Override
	public List<RecruitmentVo> adminRecruitmentList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.adminRecruitmentList");
    }

	@Override
	public List<CompanyRecruitmentVo> companyRecruitmentSelectList(int companyId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.companyRecruitmentList",companyId);
	}




    @Override
	public void jobPostFileDelete(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		sqlSession.delete("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.jobPostFileDelete", jobPostIdList);
		
	}

	@Override
	public void jobPostTagDelete(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		sqlSession.delete("com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao.jobPostTagDelete", jobPostIdList);
		
	}

	@Override
	public void jobPostStop(List<Integer> jobPostIdList) {
		// TODO Auto-generated method stub
		sqlSession.update(namespace+".jobPostStop", jobPostIdList);
		
	}

	@Override
	public List<RecruitmentVo> selectRecruitmentList(int memberId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+".selectRecruitmentList",memberId);
	}

	@Override
	public List<RecruitmentVo> recruitmentListLatest(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+".RecruitmentListLatest",searchVo);
	}

	@Override
	public List<RecruitmentVo> recruitmentListViews(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+".RecruitmentListViews",searchVo);
	}

	//채용공고에서 북마크 되어있는지 체크용(찜 구분)
	@Override
	public Integer selectBookMark(int memberId, int jobPostId) {
		// TODO Auto-generated method stub
		
		Map<String, Object> queryMap = new HashMap<>();
		
		queryMap.put("memberId", memberId);
		queryMap.put("jobPostId", jobPostId);
		
		return sqlSession.selectOne(namespace+".selectBookMark", queryMap);
	}

	//memberId 중 jobPostId에 사용중인 북마크 찾기(찜 구분)-리스트에서 사용
	@Override
	public List<Integer> bookMarkedJobPostIdList(int memberId) {
		// TODO Auto-generated method stub
		Map<String, Object> queryMap = new HashMap<>();
		
		queryMap.put("memberId", memberId);
		
		return sqlSession.selectList(namespace+".bookMarkedJobPostIdList", queryMap);
	}

    @Override
    public void deleteQuestionByOrder(Integer jobPostId, int order) {
        sqlSession.delete(namespace+".deleteQuestionByOrder", order);
    }

    @Override
    public List<JobPostFileVo> findFilesByJobPostIds(List<Integer> jobPostIds) {
        return sqlSession.selectList(namespace+".findFilesByJobPostIds", jobPostIds);
    }


}
