sts 세팅

1. https://spring.io/tools 에서 4.29.1 다운로드
2. d:\ 반디집 여기에 풀기
3. D:\workspaceFinal 로 워크스페이스 지정
4. web developer 설치
5. tern 설치
6. https://github.com/ogremage01/joblessFriends 에서 레파지토리 위치 복사
7. 레파지토리 위치 설정: D:\finalRepository\joblessFriends
8. 2_webProject 안의  JobFinder import


-------------

오라클 세팅(바뀔 수 있습니다)

1.어드민 계정으로 접속

2. 아래 구문 입력 -유저 생성
CREATE USER EODIBOJOB IDENTIFIED BY nojob6 account unlock
default tablespace users

3. admin 계정 권한 부여
grant DBA TO EODIBOJOB;

4. lombok 설치
https://projectlombok.org/download
-----

기본 페이지 명명 규칙: 카멜케이스
데이터베이스
관리자:admin
회원:member
개인회원:user
기업회원:company


스프링부트 패키지 구분

인증 관련 auth
회원 관련 member
이력서 관련resume
구인 공고 관련 recruitment
게시판 관련 community
태그 관련 tag
채팅관련 chat
