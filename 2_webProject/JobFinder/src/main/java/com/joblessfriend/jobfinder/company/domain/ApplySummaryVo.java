package com.joblessfriend.jobfinder.company.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApplySummaryVo {
    private int resumeId;
    private String memberName;
    private String resumeTitle;
    private Date applyDate;
    private String stateName;
    private int memberId;
    private int matchRate;
    private int matchScore;
}