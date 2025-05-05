//package com.joblessfriend.jobfinder.recruitment.service;
//
//import com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao;
//import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class RecruitmentServiceImpl implements RecruitmentService {
//
//    private final RecruitmentDao recruitmentDao;
//
//    public RecruitmentServiceImpl(RecruitmentDao recruitmentDao) {
//        this.recruitmentDao = recruitmentDao; // 수동 주입
//    }
//
//    public List<JobGroupVo> jobGroupList() {
//        return recruitmentDao.jobGroupList();
//    }
//}
