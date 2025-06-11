# JobFinder - 구인구직 플랫폼

## 📋 프로젝트 개요
JobFinder는 개인 구직자와 기업 채용담당자를 위한 종합 구인구직 플랫폼입니다.

## 🗄️ 데이터베이스 마이그레이션 (Oracle → MySQL)

### Oracle 11g에서 MySQL 8.0으로 마이그레이션 완료

이 프로젝트는 **AWS 프리티어 지원**을 위해 Oracle 11g에서 MySQL 8.0으로 마이그레이션되었습니다.

#### 주요 변경사항

1. **의존성 변경**
   - `ojdbc11` → `mysql-connector-java`
   - Hibernate Dialect: `OracleDialect` → `MySQLDialect`

2. **데이터베이스 설정 변경**
   ```properties
   # 변경 전 (Oracle)
   spring.datasource.url=jdbc:log4jdbc:oracle:thin:@localhost:1521:xe
   spring.datasource.username=EODIBOJOB
   
   # 변경 후 (MySQL)  
   spring.datasource.url=jdbc:log4jdbc:mysql://localhost:3306/jobfinder?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8
   spring.datasource.username=root
   ```

3. **SQL 쿼리 변경**
   - Oracle `ROWNUM` → MySQL `LIMIT`
   - Oracle `SYSDATE` → MySQL `NOW()`
   - Oracle `시퀀스.NEXTVAL` → MySQL `AUTO_INCREMENT`
   - Oracle `||` 연산자 → MySQL `CONCAT()` 함수

4. **페이지네이션 로직 수정**
   - Oracle: startRow는 1부터 시작
   - MySQL: LIMIT는 0부터 시작

#### 마이그레이션 설치 가이드

1. **MySQL 설치 및 설정**
   ```bash
   # MySQL 8.0 설치 (Windows/macOS)
   # 또는 Docker 사용
   docker run --name mysql-jobfinder -e MYSQL_ROOT_PASSWORD=your_password -d mysql:8.0
   ```

2. **데이터베이스 생성**
   ```sql
   # MySQL DDL 스크립트 실행 (다음 중 하나 선택)
   # 1. 통합 마이그레이션 스크립트
   mysql -u root -p < src/main/resources/doc/mysql_migration.sql
   
   # 2. 또는 완전한 DDL + 샘플 데이터
   mysql -u root -p < src/main/resources/doc/DB/mysql_final.sql
   mysql -u root -p < src/main/resources/doc/DB/deprecated/mysql_data_samples.sql
   ```

3. **application.properties 설정**
   ```properties
   spring.datasource.url=jdbc:log4jdbc:mysql://localhost:3306/jobfinder?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
   ```

4. **애플리케이션 실행**
   ```bash
   mvn spring-boot:run
   ```

#### AWS RDS MySQL 설정 (프리티어)

1. **AWS RDS 인스턴스 생성**
   - 엔진: MySQL 8.0
   - 인스턴스 클래스: db.t3.micro (프리티어)
   - 스토리지: 20GB (프리티어)

2. **연결 설정**
   ```properties
   spring.datasource.url=jdbc:mysql://your-rds-endpoint:3306/jobfinder
   spring.datasource.username=admin
   spring.datasource.password=your_rds_password
   ```

## 🛠️ 기술 스택

### Backend
- **Java 17**
- **Spring Boot 3.4.4**
- **Spring Security** (OAuth2 Google 로그인)
- **MyBatis 3.0.2**
- **MySQL 8.0** (Oracle 11g에서 마이그레이션)
- **Maven**

### Frontend
- **JSP/JSTL**
- **JavaScript**
- **Bootstrap**
- **AJAX**

### Database
- **MySQL 8.0** (프로덕션)
- **H2** (테스트용)

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/
│   │   └── com/joblessfriend/jobfinder/
│   │       ├── admin/          # 관리자 기능
│   │       ├── auth/           # 인증/인가
│   │       ├── chat/           # 실시간 채팅
│   │       ├── community/      # 커뮤니티
│   │       ├── company/        # 기업 회원
│   │       ├── job/            # 직무 관리
│   │       ├── member/         # 개인 회원
│   │       ├── recruitment/    # 채용공고
│   │       ├── resume/         # 이력서
│   │       ├── search/         # 검색
│   │       ├── skill/          # 스킬/태그
│   │       └── util/           # 유틸리티
   │   ├── resources/
│   │   ├── mapper/             # MyBatis SQL 매퍼
│   │   ├── static/             # 정적 리소스
│   │   ├── doc/                # 문서 및 SQL
│   │   │   ├── mysql_migration.sql      # MySQL 통합 DDL 스크립트
│   │   │   └── DB/
│   │   │       ├── mysql_final.sql      # MySQL 완전한 DDL
│   │   │       └── deprecated/
│   │   │           ├── 01table_cd_mysql.sql        # MySQL 테이블 DDL
│   │   │           └── mysql_data_samples.sql      # MySQL 샘플 데이터
│   │   └── application.properties
│   └── webapp/WEB-INF/views/   # JSP 뷰
```

## 🚀 주요 기능

### 개인회원 기능
- 회원가입/로그인 (일반, Google OAuth2)
- 이력서 작성/관리 (다중 이력서 지원)
- 채용공고 검색/지원
- 북마크/찜하기
- 지원현황 관리

### 기업회원 기능  
- 기업 회원가입/로그인
- 채용공고 작성/관리
- 지원자 관리
- 이력서 열람

### 공통 기능
- 실시간 채팅
- 커뮤니티 게시판
- 공지사항
- 검색 (통합검색, 필터검색)

### 관리자 기능
- 회원 관리
- 기업 관리  
- 채용공고 관리
- 대시보드

## 🗄️ 데이터베이스 설계

### 주요 테이블
- `member`: 개인회원
- `company`: 기업회원
- `job_post`: 채용공고
- `resume`: 이력서
- `community`: 커뮤니티 게시글
- `chat_room`, `chat_message`: 실시간 채팅

## 🔧 개발 환경 설정

### 필수 요구사항
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse 등)

### 환경변수 설정 (.env 파일)
```properties
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
MAIL_USERNAME=your_gmail_username
MAIL_PASSWORD=your_gmail_app_password
```

### 실행 방법
1. 저장소 클론
   ```bash
   git clone [repository-url]
   cd JobFinder
   ```

2. MySQL 데이터베이스 설정
   ```sql
   mysql -u root -p < src/main/resources/doc/mysql_migration.sql
   ```

3. 환경변수 설정 (.env 파일 생성)

4. 애플리케이션 실행
   ```bash
   mvn spring-boot:run
   ```

5. 브라우저에서 접속
   ```
   http://localhost:9090
   ```

## 📊 성능 최적화

- **페이지네이션**: MySQL LIMIT를 활용한 효율적인 페이징
- **인덱싱**: 검색 성능 향상을 위한 인덱스 설정
- **연관관계 최적화**: N+1 문제 해결을 위한 쿼리 최적화

## 🔐 보안

- **Spring Security**: 인증/인가 처리
- **OAuth2**: Google 소셜 로그인
- **CSRF 보호**: Spring Security 기본 설정
- **SQL Injection 방지**: MyBatis 파라미터 바인딩

## 📱 반응형 웹

- Bootstrap을 활용한 모바일 친화적 UI
- AJAX를 통한 동적 콘텐츠 로딩

## 🎯 향후 개선사항

- [ ] Redis 캐싱 적용
- [ ] 검색 엔진 최적화 (Elasticsearch)
- [ ] 실시간 알림 시스템
- [ ] 모바일 앱 개발
- [ ] API 문서화 (Swagger)

## 👥 팀원

- **Backend Developer**: [팀원명]
- **Frontend Developer**: [팀원명]  
- **Database Administrator**: [팀원명]

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 있습니다.

---

### 📞 문의사항

프로젝트 관련 문의사항이 있으시면 Issue를 등록해주세요. 