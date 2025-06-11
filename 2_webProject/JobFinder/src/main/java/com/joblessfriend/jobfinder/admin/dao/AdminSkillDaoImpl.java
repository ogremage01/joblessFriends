package com.joblessfriend.jobfinder.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Repository
public class AdminSkillDaoImpl implements AdminSkillDao {
    
    private Logger logger = LoggerFactory.getLogger(AdminSkillDaoImpl.class);
    
    @Autowired
    private SqlSession sqlSession;
    
    private String namespace = "com.joblessfriend.jobfinder.admin.dao.AdminSkillDao.";
    
    @Override
    public List<SkillVo> getSkillList(SearchVo searchVo) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", searchVo.getKeyword());
        params.put("startRow", searchVo.getStartRow());
        params.put("endRow", searchVo.getEndRow());
        
        return sqlSession.selectList(namespace + "getSkillList", params);
    }
    
    @Override
    public int getSkillCount(SearchVo searchVo) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", searchVo.getKeyword());
        
        return sqlSession.selectOne(namespace + "getSkillCount", params);
    }
    
    @Override
    public int insertSkill(String tagName, int jobGroupId) {
        Map<String, Object> params = new HashMap<>();
        params.put("tagName", tagName);
        params.put("jobGroupId", jobGroupId);
        
        return sqlSession.insert(namespace + "insertSkill", params);
    }
    
    @Override
    public int deleteSkills(List<Integer> tagIdList) {
        return sqlSession.delete(namespace + "deleteSkills", tagIdList);
    }
    
    @Override
    public List<JobGroupVo> getAllJobGroups() {
        return sqlSession.selectList(namespace + "getAllJobGroups");
    }
} 