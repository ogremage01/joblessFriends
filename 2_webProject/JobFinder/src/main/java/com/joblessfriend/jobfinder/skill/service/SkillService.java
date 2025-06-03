
package com.joblessfriend.jobfinder.skill.service;

import com.joblessfriend.jobfinder.skill.domain.SkillVo;

import java.util.List;

public interface SkillService {
    public List<SkillVo> tagList(int jobGroupId);
    List<SkillVo> postTagList(int jobPostId);
    List<SkillVo> resumeTagList(int resumeId);
    List<SkillVo> getSkillsByKeyword(String keyword);
    SkillVo getSkillById(int tagId);
}
