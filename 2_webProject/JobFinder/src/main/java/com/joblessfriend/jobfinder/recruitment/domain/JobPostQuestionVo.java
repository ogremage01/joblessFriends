package com.joblessfriend.jobfinder.recruitment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class JobPostQuestionVo {
    private Integer questionId; // int → Integer
    private Integer jobPostId;  // int → Integer
    private Integer questionOrder;
    private String questionText;

}
