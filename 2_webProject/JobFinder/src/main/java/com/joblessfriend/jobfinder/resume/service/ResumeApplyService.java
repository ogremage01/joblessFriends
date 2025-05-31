package com.joblessfriend.jobfinder.resume.service;

public interface ResumeApplyService {

    /**
     * 기존 이력서 ID를 기반으로 지원용 복사 이력서를 생성하고 저장한다.
     * @param resumeId 복사할 원본 이력서 ID
     * @param memberId 현재 로그인된 사용자 ID
     * @return 생성된 복사 이력서의 ID (resume_apply_id)
     */
    int applyResumeWithCopy(int resumeId, int memberId);

}
