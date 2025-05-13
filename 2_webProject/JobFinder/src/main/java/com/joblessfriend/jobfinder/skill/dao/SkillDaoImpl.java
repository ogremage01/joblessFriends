package com.joblessfriend.jobfinder.skill.dao;

import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SkillDaoImpl implements SkillDao{


    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<SkillVo> tagList(int jobGroupId) {
        return sqlSession.selectList("com.joblessfriend.jobfinder.skill.dao.SkillDao.tagList", jobGroupId);

    }
}
