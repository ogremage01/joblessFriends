package com.joblessfriend.jobfinder.school.service;

import com.joblessfriend.jobfinder.school.domain.SchoolInfo;
import com.joblessfriend.jobfinder.school.util.SchoolOpenApiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final SchoolOpenApiParser schoolOpenApiParser;

    @Override
    public List<SchoolInfo> getUniversityList(String keyword, String schoolType) {
        return schoolOpenApiParser.fetchUniversitiesByKeyword(keyword, schoolType);
    }
}
