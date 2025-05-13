package com.joblessfriend.jobfinder.skill.dao;

import com.joblessfriend.jobfinder.skill.domain.SkillVo;

import java.util.List;

public interface SkillDao {
    List<SkillVo> tagList(int jobGroupId);
}
