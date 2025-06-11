-- MySQL 전체 데이터 마이그레이션 스크립트
-- Oracle에서 MySQL로 변환된 모든 데이터를 순서대로 실행
-- 실행 순서: 테이블 생성 -> 기본 데이터 -> 관계 테이블 데이터

-- 1. 외래키 체크 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 2. 기본 테이블 생성 (mysql_01table_cd.sql에서)
-- 이미 생성되어 있다고 가정하거나 별도로 실행

-- 3. 기본 마스터 데이터 삽입 순서대로

-- Job Group 삽입 (참조되는 기본 테이블이므로 먼저)
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '기획·전략');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '마케팅·홍보·조사');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '회계·세무·재무');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '인사·노무·HRD');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '총무·법무·사무');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, 'IT개발·데이터');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '디자인');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '영업·판매·무역');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '고객상담·TM');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '구매·자재·물류');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '상품기획·MD');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '운전·운송·배송');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '서비스');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '생산');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '건설·건축');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '의료');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '연구·R&D');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '교육');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '미디어·문화·스포츠');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '금융·보험');
INSERT INTO JOB_GROUP (JOB_GROUP_ID, JOB_GROUP_NAME) VALUES (NULL, '공공·복지');

-- Job 데이터 삽입 (JOB_GROUP에 의존)
-- Job 데이터는 너무 많으므로 mysql_06job_data.sql 파일을 별도 실행 권장

-- 회사 데이터 삽입
-- Company 데이터는 mysql_04company_data.sql 파일을 별도 실행

-- 멤버 데이터 삽입
-- Member 데이터는 mysql_03member_data.sql 파일을 별도 실행

-- 관리자 데이터
INSERT INTO ADMIN (ADMIN_ID, PASSWORD) VALUES ('admin', 'eodibojob');
INSERT INTO admin (admin_id, password) VALUES('1','1');

-- 상태 데이터
INSERT INTO STATE (STATE_ID, STATE_NAME) VALUES (0, '불합격');
INSERT INTO STATE (STATE_ID, STATE_NAME) VALUES (1, '지원');
INSERT INTO STATE (STATE_ID, STATE_NAME) VALUES (2, '서류합격');
INSERT INTO STATE (STATE_ID, STATE_NAME) VALUES (3, '최종합격');

-- 경력 등급
INSERT INTO CAREER_GRADE (GRADE_ID, MIN_EXPERIENCE, MAX_EXPERIENCE, DESCRIPTION)
VALUES(1, 0, 1, '신입');
INSERT INTO CAREER_GRADE (GRADE_ID, MIN_EXPERIENCE, MAX_EXPERIENCE, DESCRIPTION)
VALUES(2, 1, 3, '주니어');
INSERT INTO CAREER_GRADE (GRADE_ID, MIN_EXPERIENCE, MAX_EXPERIENCE, DESCRIPTION)
VALUES(3, 3, 5, '시니어');
INSERT INTO CAREER_GRADE (GRADE_ID, MIN_EXPERIENCE, MAX_EXPERIENCE, DESCRIPTION)
VALUES(4, 5, 99, '전문가');

-- Notice Category
INSERT INTO NOTICE_CATEGORY (NOTICE_CATEGORY_ID, CATEGORY_NAME) VALUES(NULL, '이벤트');
INSERT INTO NOTICE_CATEGORY (NOTICE_CATEGORY_ID, CATEGORY_NAME) VALUES(NULL, '일반 공지');
INSERT INTO NOTICE_CATEGORY (NOTICE_CATEGORY_ID, CATEGORY_NAME) VALUES(NULL, '시스템');
INSERT INTO NOTICE_CATEGORY (NOTICE_CATEGORY_ID, CATEGORY_NAME) VALUES(NULL, '질의응답');

-- 외래키 체크 재활성화
SET FOREIGN_KEY_CHECKS = 1;

-- 추가 데이터 파일들을 순서대로 실행:
-- SOURCE mysql_03member_data.sql;
-- SOURCE mysql_04company_data.sql;
-- SOURCE mysql_06job_data.sql;
-- SOURCE mysql_10notice_data.sql;
-- SOURCE mysql_11_community_Comment_data.sql;
-- SOURCE mysql_08-2resume_data2.sql;
-- 기타 대용량 데이터 파일들...

COMMIT;

-- 참고: 대용량 데이터 파일들은 개별적으로 실행하는 것을 권장합니다:
-- 1. mysql_08-1skill_data.sql (111KB)
-- 2. mysql_09-1recruitment.sql (169KB) 
-- 3. mysql_07etc_data2.sql (178KB)
-- 4. mysql_08-2resume_data.sql (74KB)
-- 5. mysql_08-2resume_data2.sql (28KB)

-- 완료된 파일들:
-- ✅ mysql_06job_data.sql (322건) - 완료
-- ✅ mysql_11_community_Comment_data.sql (커뮤니티 데이터) - 완료
-- ✅ mysql_08-2resume_data2.sql (이력서 데이터2) - 완료 