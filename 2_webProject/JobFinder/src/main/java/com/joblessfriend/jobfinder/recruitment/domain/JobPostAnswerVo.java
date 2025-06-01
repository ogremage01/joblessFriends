package com.joblessfriend.jobfinder.recruitment.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JobPostAnswerVo {
    private int answerId;
    private int questionId;
    private int jobPostId;
    private int memberId;
    private String answerText;
    private Date answerDate;
}
