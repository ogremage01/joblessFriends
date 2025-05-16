package com.joblessfriend.jobfinder.recruitment.dao;


import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(properties = {
        "spring.jpa.database-platform=org.hibernate.dialect.OracleDialect"
})
@DisplayName("채용공고 dao 테스트")
public class RecruitmentDaoTest {

    @Autowired
    private RecruitmentDao recruitmentDao;

    @Test
    @DisplayName("공고 insert")
    void insertRecruitmentTest(){
        RecruitmentVo vo = new RecruitmentVo();
        vo.setCompanyId(1);
        vo.setTitle("백엔드 개발자 모집");
        vo.setContent("Spring, JPA 개발자 모집");
        vo.setSalary("4000");
        vo.setCareerType("신입");
        vo.setEducation("대학교 졸업(4년)");
        vo.setWorkHours("09:00~18:00");
        vo.setJobGroupId(5);
        vo.setJobId(13);
        vo.setIsContinuous(false);
        vo.setMaxApplicants(100);
        LocalDate localDate = LocalDate.of(2025, 5, 20);
        LocalDate localDate2 = LocalDate.of(2025, 5, 30);
        Date startDate = java.sql.Date.valueOf(localDate); // 변환
        Date endDate = java.sql.Date.valueOf(localDate2);
        vo.setStartDate(startDate);
        vo.setEndDate(endDate);


        recruitmentDao.insertRecruitment(vo);

        System.out.println("💡 생성된 공고 ID: " + vo.getJobPostId());
        assertThat(vo.getJobPostId()).isNotNull(); // AssertJ
    }

}
