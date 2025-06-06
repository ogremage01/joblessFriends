package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.admin.dao.AdminSkillDao;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Service
public class AdminSkillServiceImpl implements AdminSkillService {
    
    @Autowired
    private AdminSkillDao adminSkillDao;
    
    @Override
    public List<SkillVo> getSkillList(SearchVo searchVo) {
        return adminSkillDao.getSkillList(searchVo);
    }
    
    @Override
    public int getSkillCount(SearchVo searchVo) {
        return adminSkillDao.getSkillCount(searchVo);
    }
    
    @Override
    public int insertSkill(String tagName, int jobGroupId) {
        return adminSkillDao.insertSkill(tagName, jobGroupId);
    }
    
    @Override
    public int deleteSkills(List<Integer> tagIdList) {
        return adminSkillDao.deleteSkills(tagIdList);
    }
    
    @Override
    public List<JobGroupVo> getAllJobGroups() {
        return adminSkillDao.getAllJobGroups();
    }
} 