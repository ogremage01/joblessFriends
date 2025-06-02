package com.joblessfriend.jobfinder.CompanyApply;

import com.joblessfriend.jobfinder.company.domain.CompanyApplyVo;
import com.joblessfriend.jobfinder.company.service.CompanyApplyService;
import com.joblessfriend.jobfinder.util.SearchVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CompanyApplyServiceTest {

    @Autowired
    private CompanyApplyService companyApplyService;


    @Test
    public void 지원자_목록_조회_테스트() {
        // given
        SearchVo vo = new SearchVo();
        vo.setCompanyId(3);      // 실제 존재하는 company_id
        vo.setJobPostId(6);      // 실제 존재하는 job_post_id
        vo.setStartRow(1);
        vo.setEndRow(10);

        // when
        List<CompanyApplyVo> result = companyApplyService.getApplyMemberList(vo);

        // then
        assertThat(result).isNotNull();
        result.forEach(applyVo -> {
            System.out.println("지원자 ID: " + applyVo.getMember().getMemberId());
            System.out.println("이력서 이름: " + applyVo.getResumeVo().getMemberName());
            System.out.println("지원일: " + applyVo.getApplyDate());
            System.out.println("---");
        });
    }

    @Test
    public void 지원자_카운트_조회_테스트() {
        // given
        SearchVo vo = new SearchVo();
        vo.setCompanyId(3);    // 실제 존재하는 company_id
        vo.setJobPostId(6);    // 실제 존재하는 job_post_id

        // when
        int count = companyApplyService.getApplyMemberCount(vo);

        // then
        System.out.println("지원자 수: " + count);
        assertThat(count).isGreaterThanOrEqualTo(0);
    }
}
