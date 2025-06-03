
package com.joblessfriend.jobfinder.skill.service;

import com.joblessfriend.jobfinder.skill.dao.SkillDao;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService{

    @Autowired
    private SkillDao skillDao;

    @Override
    public List<SkillVo> tagList(int jobGroupId) {
        return skillDao.tagList(jobGroupId);
    }

    @Override
    public List<SkillVo> postTagList(int jobPostId) {
        return skillDao.postTagList(jobPostId);
    }

	@Override
	public List<SkillVo> resumeTagList(int resumeId) {
		// TODO Auto-generated method stub
		return skillDao.resumeTagList(resumeId);
	}
	
	@Override
	public List<SkillVo> getSkillsByKeyword(String keyword) {
		try {
			
			List<SkillVo> result = skillDao.getSkillsByKeyword(keyword);
			
			return result;
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public SkillVo getSkillById(int tagId) {
		// TODO Auto-generated method stub
		return skillDao.getSkillById(tagId);
	}
}
