package com.joblessfriend.jobfinder.recruitment.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterRequestVo {
    private List<Integer> jobIds;
    private List<String> careers;
    private List<String> educations;
    private List<Integer> skillTags;
}
