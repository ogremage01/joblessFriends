package com.joblessfriend.jobfinder.CompanyApply;

import com.joblessfriend.jobfinder.company.domain.ApplySummaryVo;
import com.joblessfriend.jobfinder.company.service.CompanyApplyService;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.service.ResumeMatchService;
import com.joblessfriend.jobfinder.resume.service.ResumeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CompanyApplyServiceTest {

    @Autowired
    private CompanyApplyService companyApplyService;
    @Autowired
    private RecruitmentService recruitmentService;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private ResumeMatchService resumeMatchService;

    @Test
    public void 전체지원자_조회_테스트() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("companyId", 2);
        paramMap.put("jobPostId", 1);

        List<ApplySummaryVo> result = companyApplyService.getApplyListByCompany(paramMap);
        assertThat(result).isNotNull();
        result.forEach(apply -> {
            System.out.println("이름: " + apply.getMemberName());
            System.out.println("이력서 제목: " + apply.getResumeTitle());
            System.out.println("지원일: " + apply.getApplyDate());
            System.out.println("상태: " + apply.getStateName());
            System.out.println("---");
        });
    }

    @Test
    public void 전체지원자_카운트_테스트() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("companyId", 2);
        paramMap.put("jobPostId", 1);

        int count = companyApplyService.countApplyByCompany(paramMap);
        System.out.println("지원자 수: " + count);
        assertThat(count).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void 페이징지원자_조회_테스트() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("companyId", 2);
        paramMap.put("jobPostId", 1);
        paramMap.put("startRow", 1);
        paramMap.put("endRow", 5);

        List<ApplySummaryVo> result = companyApplyService.getPagedApplyList(paramMap);
        assertThat(result).isNotNull();
        System.out.println("결과 수: " + result.size());
        result.forEach(apply -> System.out.println("이름: " + apply.getMemberName()));
    }

    @Test
    @DisplayName("맥스 스코어 가져오기")
    public void maxScore (){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("companyId", 2);
        paramMap.put("jobPostId", 1);
        List<ApplySummaryVo> applyList = companyApplyService.getPagedApplyList(paramMap);
        RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(1);

        int resumeId = 1;
        ResumeVo resumeVo = resumeService.getResumeByResumeId(resumeId);
        int score = resumeMatchService.calculateMatchScore(resumeVo, recruitmentVo);

        System.out.println("score: " + score);




    }

}
