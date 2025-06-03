
package com.joblessfriend.jobfinder.company.dao;

import com.joblessfriend.jobfinder.company.domain.ApplySummaryVo;
import com.joblessfriend.jobfinder.company.domain.QuestionAnswerVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CompanyApplyDaoImpl implements CompanyApplyDao {

    @Autowired
    private SqlSession sqlSession;
    private static final String NAMESPACE = "com.joblessfriend.jobfinder.company.dao.CompanyApplyDao";

    @Override
    public List<ApplySummaryVo> getApplyListByCompany(Map<String, Object> paramMap) {
        return sqlSession.selectList(NAMESPACE + ".getApplyListByCompany", paramMap);
    }

    @Override
    public int countApplyByCompany(Map<String, Object> paramMap) {
        return sqlSession.selectOne(NAMESPACE + ".countApplyByCompany", paramMap);
    }

    @Override
    public List<ApplySummaryVo> getPagedApplyList(Map<String, Object> paramMap) {
        return sqlSession.selectList(NAMESPACE + ".getPagedApplyList", paramMap);
    }
    //사전질문지//
    @Override
    public List<QuestionAnswerVo> getQuestionAnswersByJobPostAndMember(int jobPostId, int memberId) {
        Map<String, Object> param = new HashMap<>();
        param.put("jobPostId", jobPostId);
        param.put("memberId", memberId);
        return sqlSession.selectList(NAMESPACE + ".getQuestionAnswersByJobPostAndMember", param);
    }

    @Override
    public void updateResumeState(Map<String, Object> param) {
        sqlSession.update(NAMESPACE + ".updateResumeState", param);
    }

}