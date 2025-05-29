package com.joblessfriend.jobfinder.resume.domain;

import lombok.Data;

@Data
public class SchoolVo {
    private String sortation;     // 고등학교/대학교
    private String schoolName;
    private String yearOfGraduation;
    private String status;        // 졸업/재학 등
    private String majorName; // 전공명
    private String startDate; // 입학일자
    private String endDate; // 졸업일자
    private int resumeId;
    
    @Override
    public String toString() {
        return "SchoolVo{" +
                "sortation='" + sortation + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", yearOfGraduation='" + yearOfGraduation + '\'' +
                ", status='" + status + '\'' +
                ", resumeId=" + resumeId +
                '}';
    }
}