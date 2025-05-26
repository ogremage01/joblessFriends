package com.joblessfriend.jobfinder.school.service;

import com.joblessfriend.jobfinder.school.domain.MajorInfo;
import com.joblessfriend.jobfinder.school.util.MajorOpenApiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {

    private final MajorOpenApiParser majorOpenApiParser;

    @Autowired
    public MajorServiceImpl(MajorOpenApiParser majorOpenApiParser) {
        this.majorOpenApiParser = majorOpenApiParser;
    }

    @Override
    public List<MajorInfo> getMajorsByKeyword(String keyword) {
        return majorOpenApiParser.fetchMajorsByKeyword(keyword);
    }
}
