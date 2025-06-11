-- Oracle 08-2resume_data2.sql을 MySQL로 변환
-- RESUME, SCHOOL, RESUME_TAG, CAREER 테이블의 데이터

SET FOREIGN_KEY_CHECKS = 0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;

-- 기존 데이터 삭제 (역순으로)
DELETE FROM CAREER;
DELETE FROM RESUME_TAG;
DELETE FROM SCHOOL;
DELETE FROM RESUME;

-- 첫 번째 이력서 (기본 인적사항만)
INSERT INTO RESUME (
    RESUME_ID, RESUME_TITLE, NAME, BIRTHDATE, PHONENUMBER, EMAIL, POSTAL_CODE_ID, ADDRESS, SELF_INTRODUCTION, MEMBER_ID, PROFILE, CREATE_AT, MODIFIED_AT, IS_PUBLIC)
VALUES (NULL, 'AI 반도체 연구원 김가람의 이력서 기본 인적사항', '김가람', '2000-01-01', '010-1234-5687', 'user1@example.com', 14750, '305호', 'AI와 반도체 기술이 융합되는 시대에, 차세대 연산 구조를 선도하는 연구원이 되기 위해 지원하게 되었습니다.
학부 시절부터 전자공학과 인공지능을 복수전공하며, 딥러닝 모델의 하드웨어 구현 효율성에 깊은 관심을 가졌습니다.
졸업 논문에서는 CNN 모델을 FPGA에 최적화하여 성능과 전력 소비를 개선한 프로젝트를 수행하였고,
이를 통해 AI 알고리즘이 실질적인 반도체 구조로 구현되는 과정에 대한 통찰을 얻었습니다.
삼성전자 주관 AI반도체 해커톤에 참가해 Top3에 입상하며 실무 감각을 키운 경험도 있습니다.
특히, 병렬 연산 최적화 및 메모리 인터페이스 설계에 큰 흥미를 느껴 지속적인 학습을 이어가고 있습니다.
빠르게 발전하는 AI 반도체 분야에서 실질적인 기술 혁신을 이끌어내는 연구원이 되고 싶습니다.
깊이 있는 분석력과 꾸준한 학습 태도를 바탕으로, 귀 연구소의 목표에 부합하는 인재가 되겠습니다.
항상 문제의 본질을 파악하고, 창의적인 해결책을 제시하는 연구자가 되기 위해 노력하겠습니다.
감사합니다.', 1, 'profile.jpg', NOW(), NOW(), '1');

-- 두 번째 이력서 (학력 포함)
INSERT INTO RESUME (
    RESUME_ID, RESUME_TITLE, NAME, BIRTHDATE, PHONENUMBER, EMAIL, POSTAL_CODE_ID, ADDRESS, SELF_INTRODUCTION, MEMBER_ID, PROFILE, CREATE_AT, MODIFIED_AT, IS_PUBLIC)
VALUES (NULL, 'AI 반도체 연구원 김가람의 이력서 학력', '김가람', '2000-01-01', '010-1234-5687', 'user1@example.com', 14750, '305호', 'AI와 반도체 기술이 융합되는 시대에, 차세대 연산 구조를 선도하는 연구원이 되기 위해 지원하게 되었습니다. 학부 시절부터 전자공학과 인공지능을 복수전공하며, 딥러닝 모델의 하드웨어 구현 효율성에 깊은 관심을 가졌습니다. 졸업 논문에서는 CNN 모델을 FPGA에 최적화하여 성능과 전력 소비를 개선한 프로젝트를 수행하였고, 이를 통해 AI 알고리즘이 실질적인 반도체 구조로 구현되는 과정에 대한 통찰을 얻었습니다. 삼성전자 주관 AI반도체 해커톤에 참가해 Top3에 입상하며 실무 감각을 키운 경험도 있습니다. 특히, 병렬 연산 최적화 및 메모리 인터페이스 설계에 큰 흥미를 느껴 지속적인 학습을 이어가고 있습니다. 빠르게 발전하는 AI 반도체 분야에서 실질적인 기술 혁신을 이끌어내는 연구원이 되고 싶습니다. 깊이 있는 분석력과 꾸준한 학습 태도를 바탕으로, 귀 연구소의 목표에 부합하는 인재가 되겠습니다. 항상 문제의 본질을 파악하고, 창의적인 해결책을 제시하는 연구자가 되기 위해 노력하겠습니다. ', 1, 'profile.jpg', NOW(), NOW(), '1');

-- 두 번째 이력서의 학력 정보
INSERT INTO SCHOOL (
    SCHOOL_ID, RESUME_ID, SORTATION, SCHOOL_NAME, START_DATE, END_DATE, STATUS
)
VALUES (
    NULL, LAST_INSERT_ID(), 'high', '마포고등학교',
    '2014-03-02', '2017-02-28', '졸업'
);

INSERT INTO SCHOOL (
    SCHOOL_ID, RESUME_ID, SORTATION, SCHOOL_NAME, MAJOR_NAME, START_DATE, END_DATE, STATUS
)
VALUES (
    NULL, LAST_INSERT_ID(), 'univ4', '성균관대학교', '소프트웨어학과',
    '2017-03-02', '2021-02-28', '졸업'
);

INSERT INTO SCHOOL (
    SCHOOL_ID, RESUME_ID, SORTATION, SCHOOL_NAME, MAJOR_NAME, START_DATE, END_DATE, STATUS
)
VALUES (
    NULL, LAST_INSERT_ID(), 'master', '연세대학교 대학원', '컴퓨터과학과',
    '2017-03-02', '2021-02-28', '졸업'
);

-- 세 번째 이력서 (학력 + 스킬)
INSERT INTO RESUME (
    RESUME_ID, RESUME_TITLE, NAME, BIRTHDATE, PHONENUMBER, EMAIL, POSTAL_CODE_ID, ADDRESS, SELF_INTRODUCTION, MEMBER_ID, PROFILE, CREATE_AT, MODIFIED_AT, IS_PUBLIC)
VALUES (NULL, 'AI 반도체 연구원 김가람의 이력서 학력 스킬', '김가람', '2000-01-01', '010-1234-5687', 'user1@example.com', 14750, '305호', 'AI와 반도체 기술이 융합되는 시대에, 차세대 연산 구조를 선도하는 연구원이 되기 위해 지원하게 되었습니다. 학부 시절부터 전자공학과 인공지능을 복수전공하며, 딥러닝 모델의 하드웨어 구현 효율성에 깊은 관심을 가졌습니다. 졸업 논문에서는 CNN 모델을 FPGA에 최적화하여 성능과 전력 소비를 개선한 프로젝트를 수행하였고, 이를 통해 AI 알고리즘이 실질적인 반도체 구조로 구현되는 과정에 대한 통찰을 얻었습니다. 삼성전자 주관 AI반도체 해커톤에 참가해 Top3에 입상하며 실무 감각을 키운 경험도 있습니다. 특히, 병렬 연산 최적화 및 메모리 인터페이스 설계에 큰 흥미를 느껴 지속적인 학습을 이어가고 있습니다. 빠르게 발전하는 AI 반도체 분야에서 실질적인 기술 혁신을 이끌어내는 연구원이 되고 싶습니다. 깊이 있는 분석력과 꾸준한 학습 태도를 바탕으로, 귀 연구소의 목표에 부합하는 인재가 되겠습니다. 항상 문제의 본질을 파악하고, 창의적인 해결책을 제시하는 연구자가 되기 위해 노력하겠습니다. ', 1, 'profile.jpg', NOW(), NOW(), '1');

-- 세 번째 이력서 학력 정보
SET @resume3_id = LAST_INSERT_ID();

INSERT INTO SCHOOL (
    SCHOOL_ID, RESUME_ID, SORTATION, SCHOOL_NAME, START_DATE, END_DATE, STATUS
)
VALUES (
    NULL, @resume3_id, 'high', '마포고등학교',
    '2014-03-02', '2017-02-28', '졸업'
);

INSERT INTO SCHOOL (
    SCHOOL_ID, RESUME_ID, SORTATION, SCHOOL_NAME, MAJOR_NAME, START_DATE, END_DATE, STATUS
)
VALUES (
    NULL, @resume3_id, 'univ4', '성균관대학교', '소프트웨어학과',
    '2017-03-02', '2021-02-28', '졸업'
);

INSERT INTO SCHOOL (
    SCHOOL_ID, RESUME_ID, SORTATION, SCHOOL_NAME, MAJOR_NAME, START_DATE, END_DATE, STATUS
)
VALUES (
    NULL, @resume3_id, 'master', '연세대학교 대학원', '컴퓨터과학과',
    '2017-03-02', '2021-02-28', '졸업'
);

-- 세 번째 이력서 스킬 태그 (TAG_ID는 태그 테이블의 실제 ID를 참조해야 함)
INSERT INTO RESUME_TAG (RESUME_TAG_ID, RESUME_ID, TAG_ID, CREATE_AT, MODIFIED_AT)
VALUES (NULL, @resume3_id, 178, NOW(), NOW());

INSERT INTO RESUME_TAG (RESUME_TAG_ID, RESUME_ID, TAG_ID, CREATE_AT, MODIFIED_AT)
VALUES (NULL, @resume3_id, 182, NOW(), NOW());

INSERT INTO RESUME_TAG (RESUME_TAG_ID, RESUME_ID, TAG_ID, CREATE_AT, MODIFIED_AT)
VALUES (NULL, @resume3_id, 700, NOW(), NOW());

INSERT INTO RESUME_TAG (RESUME_TAG_ID, RESUME_ID, TAG_ID, CREATE_AT, MODIFIED_AT)
VALUES (NULL, @resume3_id, 702, NOW(), NOW());

-- 네 번째 이력서 (학력 + 스킬 + 경력)
INSERT INTO RESUME (
    RESUME_ID, RESUME_TITLE, NAME, BIRTHDATE, PHONENUMBER, EMAIL, POSTAL_CODE_ID, ADDRESS, SELF_INTRODUCTION, MEMBER_ID, PROFILE, CREATE_AT, MODIFIED_AT, IS_PUBLIC)
VALUES (NULL, 'AI 반도체 연구원 김가람의 이력서 학력 스킬 경력', '김가람', '2000-01-01', '010-1234-5687', 'user1@example.com', 14750, '305호', 'AI와 반도체 기술이 융합되는 시대에, 차세대 연산 구조를 선도하는 연구원이 되기 위해 지원하게 되었습니다. 학부 시절부터 전자공학과 인공지능을 복수전공하며, 딥러닝 모델의 하드웨어 구현 효율성에 깊은 관심을 가졌습니다. 졸업 논문에서는 CNN 모델을 FPGA에 최적화하여 성능과 전력 소비를 개선한 프로젝트를 수행하였고, 이를 통해 AI 알고리즘이 실질적인 반도체 구조로 구현되는 과정에 대한 통찰을 얻었습니다. 삼성전자 주관 AI반도체 해커톤에 참가해 Top3에 입상하며 실무 감각을 키운 경험도 있습니다. 특히, 병렬 연산 최적화 및 메모리 인터페이스 설계에 큰 흥미를 느껴 지속적인 학습을 이어가고 있습니다. 빠르게 발전하는 AI 반도체 분야에서 실질적인 기술 혁신을 이끌어내는 연구원이 되고 싶습니다. 깊이 있는 분석력과 꾸준한 학습 태도를 바탕으로, 귀 연구소의 목표에 부합하는 인재가 되겠습니다. 항상 문제의 본질을 파악하고, 창의적인 해결책을 제시하는 연구자가 되기 위해 노력하겠습니다. ', 1, 'profile.jpg', NOW(), NOW(), '1');

-- 네 번째 이력서 관련 데이터
SET @resume4_id = LAST_INSERT_ID();

-- 학력 정보
INSERT INTO SCHOOL (
    SCHOOL_ID, RESUME_ID, SORTATION, SCHOOL_NAME, START_DATE, END_DATE, STATUS
)
VALUES (
    NULL, @resume4_id, 'high', '마포고등학교',
    '2014-03-02', '2017-02-28', '졸업'
);

INSERT INTO SCHOOL (
    SCHOOL_ID, RESUME_ID, SORTATION, SCHOOL_NAME, MAJOR_NAME, START_DATE, END_DATE, STATUS
)
VALUES (
    NULL, @resume4_id, 'univ4', '성균관대학교', '소프트웨어학과',
    '2017-03-02', '2021-02-28', '졸업'
);

INSERT INTO SCHOOL (
    SCHOOL_ID, RESUME_ID, SORTATION, SCHOOL_NAME, MAJOR_NAME, START_DATE, END_DATE, STATUS
)
VALUES (
    NULL, @resume4_id, 'master', '연세대학교 대학원', '컴퓨터과학과',
    '2017-03-02', '2021-02-28', '졸업'
);

-- 스킬 태그
INSERT INTO RESUME_TAG (RESUME_TAG_ID, RESUME_ID, TAG_ID, CREATE_AT, MODIFIED_AT)
VALUES (NULL, @resume4_id, 178, NOW(), NOW());

INSERT INTO RESUME_TAG (RESUME_TAG_ID, RESUME_ID, TAG_ID, CREATE_AT, MODIFIED_AT)
VALUES (NULL, @resume4_id, 182, NOW(), NOW());

INSERT INTO RESUME_TAG (RESUME_TAG_ID, RESUME_ID, TAG_ID, CREATE_AT, MODIFIED_AT)
VALUES (NULL, @resume4_id, 700, NOW(), NOW());

INSERT INTO RESUME_TAG (RESUME_TAG_ID, RESUME_ID, TAG_ID, CREATE_AT, MODIFIED_AT)
VALUES (NULL, @resume4_id, 702, NOW(), NOW());

-- 경력 정보
INSERT INTO CAREER (
    CAREER_ID, RESUME_ID, COMPANY_NAME, DEPARTMENT_NAME, HIRE_YM, RESIGN_YM, POSITION, JOB_ID, JOB_GROUP_ID, SALARY, DETAIL
)
VALUES (
    NULL, @resume4_id, '네이버', '클라우드플랫폼개발실', '2021-03-01', '2022-05-31', '백엔드 개발자', 81, 6, 4800, '서버 개발 및 API 설계, 클라우드 환경 구축'
);

-- 다섯 번째 이력서 (최종본)
INSERT INTO RESUME (
    RESUME_ID, RESUME_TITLE, NAME, BIRTHDATE, PHONENUMBER, EMAIL, POSTAL_CODE_ID, ADDRESS, SELF_INTRODUCTION, MEMBER_ID, PROFILE, CREATE_AT, MODIFIED_AT, IS_PUBLIC)
VALUES (NULL, 'AI 반도체 연구원 김가람의 이력서 최종본', '김가람', '2000-01-01', '010-1234-5687', 'user1@example.com', 14750, '305호', 'AI와 반도체 기술이 융합되는 시대에, 차세대 연산 구조를 선도하는 연구원이 되기 위해 지원하게 되었습니다. 학부 시절부터 전자공학과 인공지능을 복수전공하며, 딥러닝 모델의 하드웨어 구현 효율성에 깊은 관심을 가졌습니다. 졸업 논문에서는 CNN 모델을 FPGA에 최적화하여 성능과 전력 소비를 개선한 프로젝트를 수행하였고, 이를 통해 AI 알고리즘이 실질적인 반도체 구조로 구현되는 과정에 대한 통찰을 얻었습니다. 삼성전자 주관 AI반도체 해커톤에 참가해 Top3에 입상하며 실무 감각을 키운 경험도 있습니다. 특히, 병렬 연산 최적화 및 메모리 인터페이스 설계에 큰 흥미를 느껴 지속적인 학습을 이어가고 있습니다. 빠르게 발전하는 AI 반도체 분야에서 실질적인 기술 혁신을 이끌어내는 연구원이 되고 싶습니다. 깊이 있는 분석력과 꾸준한 학습 태도를 바탕으로, 귀 연구소의 목표에 부합하는 인재가 되겠습니다. 항상 문제의 본질을 파악하고, 창의적인 해결책을 제시하는 연구자가 되기 위해 노력하겠습니다. ', 1, 'profile.jpg', NOW(), NOW(), '1');

-- 다섯 번째 이력서 관련 데이터
SET @resume5_id = LAST_INSERT_ID();

-- 학력 정보
INSERT INTO SCHOOL (
    SCHOOL_ID, RESUME_ID, SORTATION, SCHOOL_NAME, START_DATE, END_DATE, STATUS
)
VALUES (
    NULL, @resume5_id, 'high', '마포고등학교',
    '2014-03-02', '2017-02-28', '졸업'
);

INSERT INTO SCHOOL (
    SCHOOL_ID, RESUME_ID, SORTATION, SCHOOL_NAME, MAJOR_NAME, START_DATE, END_DATE, STATUS
)
VALUES (
    NULL, @resume5_id, 'univ4', '성균관대학교', '소프트웨어학과',
    '2017-03-02', '2021-02-28', '졸업'
);

INSERT INTO SCHOOL (
    SCHOOL_ID, RESUME_ID, SORTATION, SCHOOL_NAME, MAJOR_NAME, START_DATE, END_DATE, STATUS
)
VALUES (
    NULL, @resume5_id, 'master', '연세대학교 대학원', '컴퓨터과학과',
    '2017-03-02', '2021-02-28', '졸업'
);

-- 스킬 태그
INSERT INTO RESUME_TAG (RESUME_TAG_ID, RESUME_ID, TAG_ID, CREATE_AT, MODIFIED_AT)
VALUES (NULL, @resume5_id, 178, NOW(), NOW());

INSERT INTO RESUME_TAG (RESUME_TAG_ID, RESUME_ID, TAG_ID, CREATE_AT, MODIFIED_AT)
VALUES (NULL, @resume5_id, 182, NOW(), NOW());

INSERT INTO RESUME_TAG (RESUME_TAG_ID, RESUME_ID, TAG_ID, CREATE_AT, MODIFIED_AT)
VALUES (NULL, @resume5_id, 700, NOW(), NOW());

INSERT INTO RESUME_TAG (RESUME_TAG_ID, RESUME_ID, TAG_ID, CREATE_AT, MODIFIED_AT)
VALUES (NULL, @resume5_id, 702, NOW(), NOW());

-- 경력 정보 (두 개의 경력)
INSERT INTO CAREER (
    CAREER_ID, RESUME_ID, COMPANY_NAME, DEPARTMENT_NAME, HIRE_YM, RESIGN_YM, POSITION, JOB_ID, JOB_GROUP_ID, SALARY, DETAIL
)
VALUES (
    NULL, @resume5_id, '네이버', '클라우드플랫폼개발실', '2021-03-01', '2022-05-31', '백엔드 개발자', 81, 6, 4800, '서버 개발 및 API 설계, 클라우드 환경 구축'
);

INSERT INTO CAREER (
    CAREER_ID, RESUME_ID, COMPANY_NAME, DEPARTMENT_NAME, HIRE_YM, RESIGN_YM, POSITION, JOB_ID, JOB_GROUP_ID, SALARY, DETAIL
)
VALUES (
    NULL, @resume5_id, '카카오', '클라우드플랫폼개발실', '2022-06-01', '2022-10-31', '백엔드 개발자', 81, 6, 4800, '서버 개발 및 API 설계, 클라우드 환경 구축'
);

-- 김서연의 이력서 (첫 번째)
INSERT INTO RESUME (
    RESUME_ID, RESUME_TITLE, NAME, BIRTHDATE, PHONENUMBER, EMAIL, POSTAL_CODE_ID, ADDRESS, SELF_INTRODUCTION, MEMBER_ID, PROFILE, CREATE_AT, MODIFIED_AT, IS_PUBLIC)
VALUES (NULL, '백엔드/서버 개발자, 대졸, 관련 직무 경력 6년', '김서연', '1998-01-01', '010-0000-0001', 'user1@example.com', 14750, '305호', '열정적인 백엔드/서버 개발자입니다.', 2, 'profile.jpg', NOW(), NOW(), '1');

-- 김서연 이력서의 스킬 태그
SET @resume6_id = LAST_INSERT_ID();

INSERT INTO RESUME_TAG (RESUME_TAG_ID, RESUME_ID, TAG_ID, CREATE_AT, MODIFIED_AT)
VALUES (NULL, @resume6_id, 209, NOW(), NOW()); -- Java

COMMIT;
SET FOREIGN_KEY_CHECKS = 1; 