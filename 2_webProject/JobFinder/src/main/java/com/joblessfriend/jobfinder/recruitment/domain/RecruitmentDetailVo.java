package com.joblessfriend.jobfinder.recruitment.domain;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RecruitmentDetailVo {
    private RecruitmentVo recruitment;
    private CompanyVo company;
    private JobVo job;
    private List<SkillVo> skill;
    private List<WelfareVo> welfare;
}
