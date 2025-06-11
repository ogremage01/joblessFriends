# Oracle to MySQL Migration - Deprecated Folder

이 폴더에는 Oracle 11g에서 MySQL 8.0으로 마이그레이션된 모든 SQL 파일들이 포함되어 있습니다.

## 파일 구조

### Original Oracle Files (참조용)
- `01table_cd.sql` - Oracle 테이블 정의
- `02seq_cd.sql` - Oracle 시퀀스 정의
- `02-1Resume_Copy_CD,SEQ.sql` - Resume Copy 테이블과 시퀀스
- `03member_data.sql` - 멤버 데이터 (Oracle)
- `04company_data.sql` - 회사 데이터 (Oracle)
- `05job_group_data.sql` - 직업 그룹 데이터 (Oracle)
- `06job_data.sql` - 직업 데이터 (Oracle)
- `07etc_data.sql` - 기타 데이터 (Oracle)
- `07etc_data2.sql` - 추가 기타 데이터 (Oracle)
- `08-1skill_data.sql` - 스킬 데이터 (Oracle)
- `08-2resume_data.sql` - 이력서 데이터 (Oracle)
- `08-2resume_data2.sql` - 추가 이력서 데이터 (Oracle)
- `09admin_data.sql` - 관리자 데이터 (Oracle)
- `09-1recruitment.sql` - 채용공고 데이터 (Oracle)
- `09-2trigger.sql` - Oracle 트리거 (MySQL에서 제거됨)
- `10_notice_data.sql` - 공지사항 데이터 (Oracle)
- `11_community_Comment_data.sql` - 커뮤니티 댓글 데이터 (Oracle)

### MySQL Converted Files
- `mysql_01table_cd.sql` - MySQL 테이블 정의
- `mysql_02resume_copy_cd.sql` - Resume Copy 테이블 (MySQL)
- `mysql_03member_data.sql` - 멤버 데이터 (MySQL)
- `mysql_04company_data.sql` - 회사 데이터 (MySQL)
- `mysql_05job_group_data.sql` - 직업 그룹 데이터 (MySQL)
- `mysql_06job_data.sql` - 직업 데이터 (MySQL) - 322건 완료
- `mysql_07etc_data.sql` - 기타 데이터 (MySQL)
- `mysql_09admin_data.sql` - 관리자 데이터 (MySQL)
- `mysql_10notice_data.sql` - 공지사항 데이터 (MySQL)
- `mysql_11_community_Comment_data.sql` - 커뮤니티 데이터 (MySQL)
- `mysql_08-2resume_data2.sql` - 이력서 데이터2 (MySQL)
- `mysql_all_data_migration.sql` - 통합 실행 스크립트

### Sample Files
- `mysql_data_samples.sql` - MySQL 샘플 데이터
- `01table_cd_mysql.sql` - 이미 변환된 테이블 정의

## 주요 변환 사항

### 1. 데이터베이스 구조 변경
- **Oracle NUMBER** → **MySQL INT**
- **Oracle VARCHAR2** → **MySQL VARCHAR**
- **Oracle DATE** → **MySQL DATETIME**
- **Oracle CLOB** → **MySQL TEXT**
- **Oracle TIMESTAMP** → **MySQL TIMESTAMP**

### 2. 시퀀스 처리
- **Oracle SEQUENCE.NEXTVAL** → **MySQL AUTO_INCREMENT**
- 모든 PRIMARY KEY에 AUTO_INCREMENT 적용
- 시퀀스 생성 구문 제거

### 3. 함수 변환
- **Oracle SYSDATE** → **MySQL NOW()**
- **Oracle TO_DATE()** → **MySQL STR_TO_DATE() 또는 직접 날짜 형식**
- **Oracle || (문자열 연결)** → **MySQL CONCAT()**

### 4. 기타 문법 변경
- **Oracle CASCADE CONSTRAINTS** → **MySQL CASCADE**
- **Oracle ROWNUM** → **MySQL LIMIT**
- **Oracle DUAL** → **제거**
- **Oracle 트리거** → **제거 (AUTO_INCREMENT로 대체)**

## 실행 순서

### 1. 테이블 생성
```sql
SOURCE mysql_01table_cd.sql;
SOURCE mysql_02resume_copy_cd.sql;
```

### 2. 기본 데이터 삽입
```sql
SOURCE mysql_05job_group_data.sql;
SOURCE mysql_03member_data.sql;
SOURCE mysql_04company_data.sql;
SOURCE mysql_07etc_data.sql;
SOURCE mysql_09admin_data.sql;
SOURCE mysql_10notice_data.sql;
SOURCE mysql_11_community_Comment_data.sql;
SOURCE mysql_08-2resume_data2.sql;
```

### 3. 통합 실행 (권장)
```sql
SOURCE mysql_all_data_migration.sql;
```

## 미완성 변환 파일들

다음 대용량 파일들은 크기가 크므로 개별적으로 변환 작업이 필요합니다:

1. **mysql_08-1skill_data.sql** - 미변환 (111KB, 910 라인)
2. **mysql_08-2resume_data.sql** - 미변환 (74KB, 1424 라인)
3. **mysql_09-1recruitment.sql** - 미변환 (169KB, 2783 라인)
4. **mysql_07etc_data2.sql** - 미변환 (178KB, 996 라인)

## 변환 규칙 적용 예시

### Oracle 원본:
```sql
INSERT INTO MEMBER (MEMBER_ID, EMAIL, CREATE_AT) 
VALUES (MEMBER_ID_SEQ.NEXTVAL, 'test@email.com', SYSDATE);
```

### MySQL 변환:
```sql
INSERT INTO MEMBER (MEMBER_ID, EMAIL, CREATE_AT) 
VALUES (NULL, 'test@email.com', NOW());
```

## 주의사항

1. **외래키 제약조건**: 데이터 삽입 전에 `SET FOREIGN_KEY_CHECKS = 0;` 실행 권장
2. **문자 인코딩**: UTF-8 설정 확인 필요
3. **대용량 데이터**: 메모리 부족 시 배치 실행 고려
4. **트랜잭션**: 각 파일 마지막에 COMMIT 포함됨

## 테스트 방법

```sql
-- 데이터 확인
SELECT COUNT(*) FROM MEMBER;
SELECT COUNT(*) FROM COMPANY;
SELECT COUNT(*) FROM JOB_GROUP;
SELECT COUNT(*) FROM JOB;

-- AUTO_INCREMENT 확인
SHOW TABLE STATUS LIKE 'MEMBER';
```

## 문제 해결

1. **권한 오류**: MySQL 사용자에게 충분한 권한 부여 필요
2. **문자셋 오류**: `character_set_server = utf8mb4` 설정 확인
3. **외래키 오류**: 데이터 삽입 순서 확인 또는 제약조건 임시 해제

## 완료 상태

- ✅ 테이블 정의 변환 완료
- ✅ 기본 마스터 데이터 변환 완료  
- ✅ 소규모 트랜잭션 데이터 변환 완료
- ⚠️ 대용량 데이터 파일 부분 완료
- ✅ 통합 실행 스크립트 완료
- ✅ 문서화 완료

총 변환 완료: **11개 파일** / 전체: **15개 파일** (73% 완료) 