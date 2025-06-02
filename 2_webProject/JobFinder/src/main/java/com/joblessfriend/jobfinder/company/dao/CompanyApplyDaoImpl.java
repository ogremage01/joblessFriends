
package com.joblessfriend.jobfinder.company.dao;

import com.joblessfriend.jobfinder.company.domain.ApplySummaryVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}