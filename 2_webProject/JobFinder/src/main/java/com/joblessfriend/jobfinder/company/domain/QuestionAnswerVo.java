package com.joblessfriend.jobfinder.company.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionAnswerVo {
    private int questionOrder;
    private String questionText;
    private String answerText;
}
