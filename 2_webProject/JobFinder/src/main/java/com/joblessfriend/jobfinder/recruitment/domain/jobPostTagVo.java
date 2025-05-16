package com.joblessfriend.jobfinder.recruitment.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class jobPostTagVo {

    private int jobPost;    // job_post_id
    private int tagId;      // tag_id (스킬)
    private int required;   // 0 또는 1

}
