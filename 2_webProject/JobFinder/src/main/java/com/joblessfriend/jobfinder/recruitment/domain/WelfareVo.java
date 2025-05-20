package com.joblessfriend.jobfinder.recruitment.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WelfareVo {
    private int jobWelfareId; //복지id
    private int jobPostId; //채용공고id
    private String benefitText; //복지 내용
}
