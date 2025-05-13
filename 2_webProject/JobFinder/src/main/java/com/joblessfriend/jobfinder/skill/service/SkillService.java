package com.joblessfriend.jobfinder.skill.service;

import com.joblessfriend.jobfinder.skill.domain.SkillVo;

import java.util.List;

public interface SkillService {
    public List<SkillVo> tagList(int jobGroupId);
}
