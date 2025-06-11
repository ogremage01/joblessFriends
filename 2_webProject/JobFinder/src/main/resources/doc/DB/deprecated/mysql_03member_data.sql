-- MySQL 멤버 데이터 삽입 파일
-- Oracle MEMBER_ID_SEQ.NEXTVAL -> MySQL AUTO_INCREMENT (NULL 사용)
-- Oracle SYSDATE -> MySQL NOW()

INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'user1@example.com', '1111', '이력서마스터', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'random2@testmail.com', '1111', '잡고래', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'data3@demo.net', '1111', '연봉탐색기', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'fake4@sample.org', '1111', '커리어친구', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'dummy5@email.com', '1111', '잡줍줍', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'info6@mockdata.net', '1111', '일단지원함', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'random7@trymail.com', '1111', '커리어상담소', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'demo8@testexample.org', '1111', '채용소식통', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'sample9@mailbox.net', '1111', '워라밸러', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'trial10@dummydata.com', '1111', '채용에진심', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'test11@randommail.org', '1111', '커리어길잡이', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'mock12@fakemail.net', '1111', '취업파이터', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'fake13@tryexample.com', '1111', '착한친구', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'sample14@demomail.net', '1111', '최과장', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'generated15@mocktest.org', '1111', '아이스아메리카노', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'email16@samplebox.com', '1111', '취직시켜줘', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'random17@testdomain.net', '1111', '도마뱀부장', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'data18@fakedemo.com', '1111', '잡문가', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'dummy19@examplemail.org', '1111', '이력서자판기', 5, NOW(), NOW());
INSERT INTO member (member_ID, Email, Password, NICKNAME, Resume_Max, CREATE_AT, MODIFIED_AT) VALUES (NULL, 'user20@mockexample.net', '1111', '스무고개', 5, NOW(), NOW());

-- MySQL은 자동 커밋이 기본이므로 commit 불필요하지만 명시적으로 남겨둠
COMMIT; 