package com.joblessfriend.jobfinder.recruitment.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobGroupVo {
    private int jobGroupId;
    private String jobGroupName;
    private String jobName;
    private int jobId;
    private int postCount;
}