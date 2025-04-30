sts 세팅

1. https://spring.io/tools 에서 4.29.1 다운로드
2. d:\ 반디집 여기에 풀기
3. D:\workspaceFinal 로 워크스페이스 지정
4. tern 설치
5. https://github.com/ogremage01/joblessFriends 에서 레파지토리 위치 복사
6. 레파지토리 위치 설정: D:\finalRepository\joblessFriends
7. 2_webProject 안의  JobFinder import


-------------

오라클 세팅

1.어드민 계정으로 접속

2. 아래 구문 입력 -유저 생성
CREATE USER EODIBOJOB IDENTIFIED BY nojob6 account unlock
default tablespace users

3. admin 계정 권한 부여
grant DBA TO EODIBOJOB;
