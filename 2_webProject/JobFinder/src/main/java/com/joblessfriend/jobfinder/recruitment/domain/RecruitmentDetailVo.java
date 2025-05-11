package com.joblessfriend.jobfinder.recruitment.domain;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.job.domain.JobVo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RecruitmentDetailVo {
    private RecruitmentVo recruitment;
    private CompanyVo company;
    private JobVo job;

}
