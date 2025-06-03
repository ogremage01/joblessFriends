package com.joblessfriend.jobfinder.skill.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SkillVo {
    private int tagId;
    private String tagName;
    private Date createDate;
    private Date modifiedDate;
    private int jobGroupId;
    private String category;
    private int resumeTag;

}
