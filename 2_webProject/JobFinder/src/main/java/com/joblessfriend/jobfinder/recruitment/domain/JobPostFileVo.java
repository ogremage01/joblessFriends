package com.joblessfriend.jobfinder.recruitment.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class JobPostFileVo {
    private int jobPostFileId;
    private Integer jobPostId; // 공고 등록 후 갱신 예정
    private String fileName;
    private String storedFileName;
    private String fileExtension;
    private String tempKey;
    private long fileSize;
    private Date createAt;
    private Date modifiedAt;
}