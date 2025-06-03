
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

    @Override
    public List<SkillVo> postTagList(int jobPostId) {
        return sqlSession.selectList("com.joblessfriend.jobfinder.skill.dao.SkillDao.postTagList", jobPostId);
    }

	@Override
	public List<SkillVo> resumeTagList(int resumeId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.joblessfriend.jobfinder.skill.dao.SkillDao.resumeTagList", resumeId);
	}
	
	@Override
	public List<SkillVo> getSkillsByKeyword(String keyword) {
		return sqlSession.selectList("com.joblessfriend.jobfinder.skill.dao.SkillDao.getSkillsByKeyword", keyword);
	}

	@Override
	public SkillVo getSkillById(int tagId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("com.joblessfriend.jobfinder.skill.dao.SkillDao.getSkillById", tagId);
	}
}
