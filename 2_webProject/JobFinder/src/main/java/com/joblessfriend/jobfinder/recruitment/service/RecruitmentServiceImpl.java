package com.joblessfriend.jobfinder.recruitment.service;

import com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao;
import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.skill.dao.SkillDao;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.util.SearchVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RecruitmentServiceImpl implements RecruitmentService {

    @Autowired
    private RecruitmentDao recruitmentDao;

    @Autowired
    private SkillDao skillDao;


    public List<JobGroupVo> jobGroupList() {
        return recruitmentDao.jobGroupList();
    }

    @Override
    public List<JobGroupVo> jobList(int jobGroupId) {
        return recruitmentDao.jobList(jobGroupId);
    }

    @Override
    public int getRecruitmentTotalCount(SearchVo searchVo) {
        return recruitmentDao.getRecruitmentTotalCount(searchVo);
    }

    @Override
    public List<RecruitmentVo> recruitmentList(SearchVo searchVo) {
        return recruitmentDao.recruitmentList(searchVo);
    }
    @Override
    public RecruitmentVo getRecruitmentId(int jobPostId) {
        return recruitmentDao.getRecruitmentId(jobPostId);
    }
    @Override
    public List<WelfareVo> selectWelfareByJobPostId(int jobPostId) {
        return recruitmentDao.selectWelfareByJobPostId(jobPostId);
    }



    @Override
    @Transactional
    public void jobPostDelete(List<Integer> jobPostIdList) {

        // ✅ 1. 삭제 대상 파일 리스트 먼저 조회
        List<JobPostFileVo> files = recruitmentDao.findFilesByJobPostIds(jobPostIdList);

        // ✅ 2. DB 삭제 (ON DELETE CASCADE와 함께 연관된 자식 테이블 정리)
        recruitmentDao.jobPostFileDelete(jobPostIdList);  // 필요 시 명시적 삭제
        recruitmentDao.jobPostTagDelete(jobPostIdList);
        recruitmentDao.jobPostDelete(jobPostIdList);      // JOB_POST 삭제

        // ✅ 3. 실제 파일 삭제
        for (JobPostFileVo file : files) {
            System.out.println("삭제할 파일이름:   "+file.getStoredFileName());
            deleteFileFromSystem(file.getStoredFileName(), "C:/upload/job_post/thumbs/");
            deleteFileFromSystem(file.getStoredFileName(), "C:/upload/job_post/");
        }
    }

    // 파일 시스템에서 파일 삭제하는 유틸리티 메서드
    private void deleteFileFromSystem(String fileName, String uploadDir) {
        try {
            // URL 형태인 경우 파일명만 추출
            if (fileName.startsWith("/")) {
                fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            }

            File fileToDelete = new File(uploadDir + fileName);
            if (fileToDelete.exists()) {
                boolean deleted = fileToDelete.delete();
                if (deleted) {
                    System.out.println("파일 삭제 성공: " + fileName);
                } else {
                    System.out.println("파일 삭제 실패: " + fileName);
                }
            } else {
                System.out.println("삭제할 파일이 존재하지 않음: " + fileName);
            }
        } catch (Exception e) {
            System.err.println("파일 삭제 중 오류 발생: " + fileName + ", " + e.getMessage());
        }
    }





    @Transactional
    @Override
    public void insertRecruitment(RecruitmentVo recruitmentVo, List<Integer> tagIdList, List<WelfareVo> welfareList) {
        // 1. 메인 채용공고 insert → selectKey로 jobPostId 생성됨
        System.out.println("🚀 service 들어옴");
        recruitmentDao.insertRecruitment(recruitmentVo);


        // 2. 태그 연결
        recruitmentDao.insertJobPostTag(recruitmentVo, tagIdList);

        // 3. 복리후생 jobPostId 세팅
        int jobPostId = recruitmentVo.getJobPostId();
        for (WelfareVo vo : welfareList) {
            vo.setJobPostId(jobPostId);
            recruitmentDao.insertJobPostWelfare(vo); // 단건 삽입
        }

        if (recruitmentVo.getQuestionList() != null && !recruitmentVo.getQuestionList().isEmpty()) {
            for (JobPostQuestionVo questionVo : recruitmentVo.getQuestionList()) {
                questionVo.setJobPostId(jobPostId); // FK 설정
                recruitmentDao.updateQuestionTextByOrder(questionVo);
            }
        }


        if (recruitmentVo.getTempKey() != null && !recruitmentVo.getTempKey().isBlank()) {
            recruitmentDao.updateJobPostIdByTempKey( recruitmentVo.getJobPostId(), recruitmentVo.getTempKey());
        }
    }

    //업데이트라인 //
    @Override
    @Transactional
    public void updateRecruitment(RecruitmentVo vo, List<Integer> tagList, List<WelfareVo> welfareList, String tempKey){
        // 1. 메인 테이블 UPDATE
        recruitmentDao.updateRecruitment(vo);

        // 2. 기존 태그 삭제 후 다시 삽입
        recruitmentDao.deleteTagsByJobPostId(vo.getJobPostId());
        recruitmentDao.insertJobPostTag(vo, tagList);

        // 3. 기존 복리후생 삭제 후 다시 삽입
        recruitmentDao.deleteWelfareByJobPostId(vo.getJobPostId());
        for (WelfareVo welfare : welfareList) {
            welfare.setJobPostId(vo.getJobPostId());
            recruitmentDao.insertJobPostWelfare(welfare);
        }

        // 4. 이미지가 새로 업로드되어 tempKey가 있는 경우, 파일 테이블 갱신
        if (tempKey != null && !tempKey.isBlank()) {
            recruitmentDao.updateJobPostIdByTempKey(vo.getJobPostId(), tempKey);
        }

        List<JobPostQuestionVo> existingList = recruitmentDao.getRecruitmentQuestion(vo.getJobPostId());
        List<JobPostQuestionVo> newList = vo.getQuestionList();

// 1~3번 순회
        // 기존 질문에서 newList에 없는 ORDER는 모두 제거
        Set<Integer> newOrderSet = newList.stream()
                .map(JobPostQuestionVo::getQuestionOrder)
                .collect(Collectors.toSet());



// 그다음, insert or update
        for (JobPostQuestionVo question : newList) {
            question.setJobPostId(vo.getJobPostId());
            Optional<JobPostQuestionVo> match = existingList.stream()
                    .filter(e -> e.getQuestionOrder() == question.getQuestionOrder())
                    .findFirst();
            if (match.isPresent()) {
                recruitmentDao.updateQuestionTextByOrder(question);
            } else {
                recruitmentDao.insertQuestion(question);
            }
        }


        if (tempKey != null && !tempKey.isBlank()) {
            recruitmentDao.updateJobPostIdByTempKey(vo.getJobPostId(), tempKey);
        }

    }

    @Override
    public void updateQuestionTextByOrder(JobPostQuestionVo questionVo) {
        recruitmentDao.updateQuestionTextByOrder(questionVo);
    }


    @Override
    public void deleteTagsByJobPostId(int jobPostId) {
        recruitmentDao.deleteTagsByJobPostId(jobPostId);
    }

    @Override
    public void deleteAnswersByJobPostId(int jobPostId) {
        recruitmentDao.deleteAnswersByJobPostId(jobPostId);
    }

    @Override
    public void deleteWelfareByJobPostId(int jobPostId) {
        recruitmentDao.deleteWelfareByJobPostId(jobPostId);
    }

    @Override
    public void increaseViews(int jobPostId) {
        recruitmentDao.increaseViews(jobPostId);
    }

    @Override
    public void insertJobPostFile(JobPostFileVo fileVo) {
        recruitmentDao.insertJobPostFile(fileVo);
    }
    @Override
    public void updateJobPostIdByTempKey(int jobPostId,String tempKey) {
        recruitmentDao.updateJobPostIdByTempKey(jobPostId ,tempKey);
    }

    @Override
    public int countFilteredPosts(FilterRequestVo filterRequestVo) {
        return recruitmentDao.countFilteredPosts(filterRequestVo);
    }

    @Override
    public List<JobPostQuestionVo> getRecruitmentQuestion(int jobPostId) {
        return recruitmentDao.getRecruitmentQuestion(jobPostId);
    }

    @Override
    public List<RecruitmentVo> getFilteredRecruitmentList(FilterRequestVo filterRequestVo) {
        return recruitmentDao.getFilteredRecruitmentList(filterRequestVo);
    }
    @Override
    public int getFilteredRecruitmentTotalCount(FilterRequestVo filterRequestVo) {
        return recruitmentDao.getFilteredRecruitmentTotalCount(filterRequestVo);
    }
    //카운트필터

	@Override
	public boolean checkCompanyOwnsJobPost(int companyId, int jobPostId) {
	    RecruitmentVo recruitment = getRecruitmentId(jobPostId);
	    return recruitment != null && recruitment.getCompanyId() == companyId;
	}

	@Override
	public List<RecruitmentVo> recruitmentListLatest(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return recruitmentDao.recruitmentListLatest(searchVo);
	}

	@Override
	public List<RecruitmentVo> recruitmentListViews(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return recruitmentDao.recruitmentListViews(searchVo);
	}
	
	//(찜했는지 확인용)(찜 구분)
	@Override
	public Integer selectBookMark(int memberId, int jobPostId) {
		// TODO Auto-generated method stub
		return recruitmentDao.selectBookMark(memberId, jobPostId);
	}

	//memberId 중 jobPostId에 사용중인 북마크 찾기(찜 구분)-리스트에서 사용
	@Override
	public List<Integer> bookMarkedJobPostIdList(int memberId) {
		// TODO Auto-generated method stub
		return recruitmentDao.bookMarkedJobPostIdList(memberId);
	}





}
