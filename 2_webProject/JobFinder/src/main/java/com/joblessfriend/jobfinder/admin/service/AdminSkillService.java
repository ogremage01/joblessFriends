package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.util.SearchVo;

public interface AdminSkillService {
    
    List<SkillVo> getSkillList(SearchVo searchVo);
    
    int getSkillCount(SearchVo searchVo);
    
    int insertSkill(String tagName, int jobGroupId);
    
    int deleteSkills(List<Integer> tagIdList);
    
    List<JobGroupVo> getAllJobGroups();
} 