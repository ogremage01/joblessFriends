--관리자
INSERT INTO ADMIN (ADMIN_ID, PASSWORD) VALUES ('admin', 'eodibojob');

--이력서 지원 관리 상태
INSERT INTO STATE (STATE_ID, STATE_NAME) VALUES (state_id_seq.nextval, '대기');
INSERT INTO STATE (STATE_ID, STATE_NAME) VALUES (state_id_seq.nextval, '합격');
INSERT INTO STATE (STATE_ID, STATE_NAME) VALUES (state_id_seq.nextval, '불합격');

--태그
INSERT INTO TAG (TAG_ID, TAG_NAME, CREATE_AT, MODIFIED_AT, JOB_GROUP_ID, JOB_ID) 
VALUES(tag_id_seq.nextval, sysdate, sysdate, 'JSP', 6, 85);

--이력서
--INSERT INTO RESUME (RESUME_ID, NAME, BIRTHDATE, PHONENUMBER, EMAIL) 
--VALUES(RESUME_ID_SEQ.nextval, );

--자격증
--INSERT INTO CERTIFICATE (CERTIFICATE_ID, Resume_ID, CERTIFICATE_NAME, ACQUISITION_DATE, ISSUING_AUTHORITY) 
--VALUES(certificate_id_seq.nextval, 1, '시각디자인기사', '2000-05-05', '한국산업인력공단');

--경력
--INSERT INTO Career (CAREER_ID, Resume_ID, COMPANY_NAME, DEPARTMENT_NAME, HIRE_YM, RESIGN_YM, POSITION, JOB_TITLE, SALARY)
--VALUES (career_id_seq.nextval, 1, '기업은행', '영업', '2020-03', '2022-09', '대리', '?', '?', 3000);

--교육
--INSERT INTO EDUCATION (EDU_ID, Resume_ID, EDU_INSTITUTION, EDU_NAME, START_DATE, END_DATE)
--VALUES (edu_id_seq.nextval, 1, '더조은 컴퓨터 아카데미', 'AWS', '2024-12-04', '2025-06-13');

--학력
--INSERT INTO SCHOOL (SCHOOL_ID, Resume_ID, SORTATION, SCHOOL_NAME, YEAR_OF_GRADUATION, STATUS)
--VALUES (SCHOOL_ID_SEQ.nextval, 1, '대학교', '서울대학교', '2024', '졸업');

--포트폴리오
--INSERT INTO PORTFOLIO (PORTFOLIO_ID, Resume_ID, FILE_NAME, STORED_FILE_NAME, CREATE_AT, MODIFIED_AT )
--VALUES ();

--채용공고
--INSERT INTO JOB_POST (JOB_POST_ID, COMPANY_ID, TITLE, CONTENT, YEAR_OF_GRADUATION, STATUS)
--VALUES (JOB_POST_ID_);

--이력서 관리
--INSERT INTO JOB_POST (RM_ID, JOB_POST_ID, MEMBER_ID, RESUME_FILE, STATE_ID)
 
--이력서 태그

--게시판
INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (COMMUNITY_ID_SEQ.nextval, 1, '면접 제의 받으셨나요?', '안녕하세요.

오늘은 면접 가시기전에 체크해 보셨으면 하는 점을 알려드리겠습니다.

혹시 업체이서 사무실, 매장이 아닌 곳에서 면접을 보길 원한다면 의심을 해보세요?

일반적인 업체라면 사업을 운영하는 실체가 있기 마련입니다.

뜬금없이 **지하철역에서 전화를 달라거나, 정확한 근무지 안내없이 면접을 요청을 한다면 다시한번 체크해보세요.

한번더 생각하는 습관이 안전한 일자리를 찾을수 있습니다.', sysdate, sysdate, 0);

--댓글
INSERT INTO POST_COMMENT (POST_COMMENT_ID, COMMUNITY_ID, MEMBER_ID, CONTENT, CREATE_AT, MODIFIED_AT)
VALUES (POST_COMMENT_ID_SEQ.nextval, 1, 1, '정말 필요한 정보네요!', sysdate, sysdate);

--대댓
INSERT INTO REPLY (REPLY_ID, POST_COMMENT_ID, MEMBER_ID, COMMENT_CONTENT, CREATE_AT, MODIFIED_AT)
VALUES (REPLY_ID_SEQ.nextval, 1, 2, '그러게요!', sysdate, sysdate);

--채팅방
--INSERT INTO CHATROOM (CR_ID, MEMBER_ID, ADMIN_ID, CREATE_AT)
--VALUES ();

--채팅 메시지
--INSERT INTO JOB_POST (CM_ID, CR_ID, CONTENT, READ, ADMIN_ID, COMPANY_ID)
--VALUES ();

--우편번호,  채용공고 태그, 북마크(아마 채용공고), 커뮤니티(게시판) 파일

