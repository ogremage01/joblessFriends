package com.joblessfriend.jobfinder.school.service;

import com.joblessfriend.jobfinder.school.domain.MajorInfo;
import java.util.List;

public interface MajorService {
    List<MajorInfo> getMajorsByKeyword(String keyword);
}
