package com.joblessfriend.jobfinder.resume.dao;

import com.joblessfriend.jobfinder.recruitment.domain.JobPostAnswerVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.service.ResumeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class resumeDaoTest {

    @Autowired
    ResumeApplyDao resumeApplyDao;
    @Autowired
    ResumeService resumeService;
    @Test
    @DisplayName("사전질문 답변지 인서트확인")
    void insertResumeApply() {



        //given
                List<JobPostAnswerVo> answerList = new ArrayList<>();
                JobPostAnswerVo answerVo = new JobPostAnswerVo();
                answerVo.setJobPostId(4);
//                answerVo.setAnswerId(1); 시퀀스 자동생산 //
                answerVo.setQuestionId(4);
                answerVo.setMemberId(1);
                answerVo.setAnswerText("그냥 지원했습니다.");
                answerVo.setAnswerDate(new Date());
            answerList.add(answerVo);
            List<JobPostAnswerVo> answerList1 = new ArrayList<>();
            JobPostAnswerVo answerVo1 = new JobPostAnswerVo();
            answerVo1.setJobPostId(4);
            answerVo1.setQuestionId(5);
            answerVo1.setMemberId(1);
            answerVo1.setAnswerText("그냥 지원했습니다.");
            answerList1.add(answerVo1);

        //when
//            int insertTed = resumeApplyDao.insertAnswers(answerList);
        //then
//            assertEquals(1, insertTed);
    }


    @Test
    public void testResumeWithAllDetails() {
        ResumeVo resume = resumeService.getResumeWithAllDetails(3);


        System.out.println("주소값" + resume);
        System.out.println("학교 수: " + resume.getSchoolList().size());
        System.out.println("스킬 수: " + resume.getSkillList().size());
        System.out.println();


    }

}
