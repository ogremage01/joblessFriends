package com.joblessfriend.jobfinder.resume.domain;

import lombok.Data;

@Data
public class SchoolVo {
    private String sortation;     // 고등학교/대학교
    private String schoolName;
    private String yearOfGraduation;
    private String status;        // 졸업/재학 등
    private int resumeId;
}