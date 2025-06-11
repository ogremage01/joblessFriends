-- JobFinder MySQL 데이터베이스 - 완전한 DDL 및 데이터 스크립트
-- Oracle 11g에서 MySQL 8.0으로 변환

-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS jobfinder 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE jobfinder;

-- 기존 테이블 삭제 (순서 중요)
SET foreign_key_checks = 0;

DROP TABLE IF EXISTS chat_message;
DROP TABLE IF EXISTS chat_room;
DROP TABLE IF EXISTS job_post_answer;
DROP TABLE IF EXISTS job_post_question;
DROP TABLE IF EXISTS job_post_file;
DROP TABLE IF EXISTS job_post_welfare;
DROP TABLE IF EXISTS job_post_tag;
DROP TABLE IF EXISTS bookMark;
DROP TABLE IF EXISTS resume_manage;
DROP TABLE IF EXISTS portfolio_copy;
DROP TABLE IF EXISTS portfolio;
DROP TABLE IF EXISTS certificate_resume_copy;
DROP TABLE IF EXISTS certificate_resume;
DROP TABLE IF EXISTS resume_tag_copy;
DROP TABLE IF EXISTS resume_tag;
DROP TABLE IF EXISTS career_copy;
DROP TABLE IF EXISTS career;
DROP TABLE IF EXISTS education_copy;
DROP TABLE IF EXISTS education;
DROP TABLE IF EXISTS school_copy;
DROP TABLE IF EXISTS school;
DROP TABLE IF EXISTS resume_copy;
DROP TABLE IF EXISTS resume;
DROP TABLE IF EXISTS profile_temp;
DROP TABLE IF EXISTS community_file;
DROP TABLE IF EXISTS reply;
DROP TABLE IF EXISTS post_comment;
DROP TABLE IF EXISTS community;
DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS notice_category;
DROP TABLE IF EXISTS job_post;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS job;
DROP TABLE IF EXISTS job_group;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS state;
DROP TABLE IF EXISTS certificate;

SET foreign_key_checks = 1;

-- ===============================
-- 기본 테이블 생성
-- ===============================

-- STATE 테이블 (지원상태)
CREATE TABLE state (
    state_id INT AUTO_INCREMENT PRIMARY KEY,
    state_name VARCHAR(50) NOT NULL
);

-- ADMIN 테이블 (관리자)
CREATE TABLE admin (
    admin_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL
);

-- MEMBER 테이블 (개인회원)
CREATE TABLE member (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    resume_max INT DEFAULT 5,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    provider VARCHAR(20) DEFAULT 'normal'
);

-- COMPANY 테이블 (기업회원)
CREATE TABLE company (
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    brn VARCHAR(20) NOT NULL,
    representative VARCHAR(50) NOT NULL,
    tel VARCHAR(20),
    postal_code_id INT,
    address VARCHAR(200),
    arena_name VARCHAR(100),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- JOB_GROUP 테이블 (직군)
CREATE TABLE job_group (
    job_group_id INT AUTO_INCREMENT PRIMARY KEY,
    job_group_name VARCHAR(50) NOT NULL
);

-- JOB 테이블 (직무)
CREATE TABLE job (
    job_id INT AUTO_INCREMENT PRIMARY KEY,
    job_group_id INT,
    job_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (job_group_id) REFERENCES job_group(job_group_id) ON DELETE CASCADE
);

-- CERTIFICATE 테이블 (자격증 마스터)
CREATE TABLE certificate (
    certificate_id INT AUTO_INCREMENT PRIMARY KEY,
    certificate_name VARCHAR(100) NOT NULL,
    issuing_authority VARCHAR(100),
    is_active TINYINT DEFAULT 1
);

-- TAG 테이블 (스킬태그)
CREATE TABLE tag (
    tag_id INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    job_group_id INT,
    FOREIGN KEY (job_group_id) REFERENCES job_group(job_group_id) ON DELETE CASCADE
);

-- JOB_POST 테이블 (채용공고)
CREATE TABLE job_post (
    job_post_id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    salary VARCHAR(100),
    work_hours VARCHAR(50),
    job_id INT,
    job_group_id INT,
    views INT DEFAULT 0,
    job_img VARCHAR(500),
    career_type VARCHAR(20),
    education VARCHAR(20),
    template_type INT,
    start_date DATE,
    end_date DATE,
    is_continuous TINYINT DEFAULT 0,
    max_applicants INT DEFAULT 0,
    current_applicants INT DEFAULT 0,
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES company(company_id) ON DELETE CASCADE,
    FOREIGN KEY (job_id) REFERENCES job(job_id),
    FOREIGN KEY (job_group_id) REFERENCES job_group(job_group_id)
);

-- JOB_POST_WELFARE 테이블 (복리후생)
CREATE TABLE job_post_welfare (
    job_welfare_id INT AUTO_INCREMENT PRIMARY KEY,
    job_post_id INT NOT NULL,
    benefit_text VARCHAR(500) NOT NULL,
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE
);

-- JOB_POST_TAG 테이블 (채용공고-스킬 연결)
CREATE TABLE job_post_tag (
    job_post INT NOT NULL,
    tag_id INT NOT NULL,
    PRIMARY KEY (job_post, tag_id),
    FOREIGN KEY (job_post) REFERENCES job_post(job_post_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id) ON DELETE CASCADE
);

-- JOB_POST_FILE 테이블 (채용공고 파일)
CREATE TABLE job_post_file (
    job_post_file_id INT AUTO_INCREMENT PRIMARY KEY,
    job_post_id INT NOT NULL,
    file_name VARCHAR(200),
    stored_file_name VARCHAR(200),
    file_size BIGINT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    file_extension VARCHAR(10),
    file_link VARCHAR(500),
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE
);

-- JOB_POST_QUESTION 테이블 (채용공고 질문)
CREATE TABLE job_post_question (
    job_post_question_id INT AUTO_INCREMENT PRIMARY KEY,
    job_post_id INT NOT NULL,
    question_text TEXT,
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE
);

-- JOB_POST_ANSWER 테이블 (채용공고 답변)
CREATE TABLE job_post_answer (
    job_post_answer_id INT AUTO_INCREMENT PRIMARY KEY,
    job_post_question_id INT NOT NULL,
    member_id INT NOT NULL,
    answer_text TEXT,
    answer_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_post_question_id) REFERENCES job_post_question(job_post_question_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

-- PROFILE_TEMP 테이블 (임시 프로필)
CREATE TABLE profile_temp (
    upload_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT,
    file_name VARCHAR(200),
    is_used TINYINT DEFAULT 0,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

-- RESUME 테이블 (이력서)
CREATE TABLE resume (
    resume_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_title VARCHAR(100),
    name VARCHAR(50) NOT NULL,
    birthdate DATE,
    phonenumber VARCHAR(20),
    email VARCHAR(100),
    postal_code_id INT,
    address VARCHAR(200),
    self_introduction TEXT,
    member_id INT NOT NULL,
    profile VARCHAR(500),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_public TINYINT DEFAULT 0,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

-- RESUME_COPY 테이블 (이력서 사본)
CREATE TABLE resume_copy (
    resume_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_title VARCHAR(100),
    name VARCHAR(50) NOT NULL,
    birthdate DATE,
    phonenumber VARCHAR(20),
    email VARCHAR(100),
    postal_code_id INT,
    address VARCHAR(200),
    self_introduction TEXT,
    member_id INT NOT NULL,
    profile VARCHAR(500),
    match_score DECIMAL(5,2),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_public TINYINT DEFAULT 0
);

-- SCHOOL 테이블 (학력)
CREATE TABLE school (
    school_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    sortation VARCHAR(20),
    school_name VARCHAR(100),
    year_of_graduation VARCHAR(10),
    status VARCHAR(20),
    major_name VARCHAR(50),
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

-- SCHOOL_COPY 테이블 (학력 사본)
CREATE TABLE school_copy (
    school_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    sortation VARCHAR(20),
    school_name VARCHAR(100),
    year_of_graduation VARCHAR(10),
    status VARCHAR(20),
    major_name VARCHAR(50),
    start_date DATE,
    end_date DATE
);

-- EDUCATION 테이블 (교육)
CREATE TABLE education (
    edu_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    edu_institution VARCHAR(100),
    edu_name VARCHAR(100),
    start_date DATE,
    end_date DATE,
    content TEXT,
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

-- EDUCATION_COPY 테이블 (교육 사본)
CREATE TABLE education_copy (
    edu_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    edu_institution VARCHAR(100),
    edu_name VARCHAR(100),
    start_date DATE,
    end_date DATE,
    content TEXT
);

-- CAREER 테이블 (경력)
CREATE TABLE career (
    career_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    company_name VARCHAR(100),
    department_name VARCHAR(50),
    hire_ym DATE,
    resign_ym DATE,
    position VARCHAR(50),
    job_id INT,
    job_group_id INT,
    salary VARCHAR(50),
    detail TEXT,
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE,
    FOREIGN KEY (job_id) REFERENCES job(job_id),
    FOREIGN KEY (job_group_id) REFERENCES job_group(job_group_id)
);

-- CAREER_COPY 테이블 (경력 사본)
CREATE TABLE career_copy (
    career_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    company_name VARCHAR(100),
    department_name VARCHAR(50),
    hire_ym DATE,
    resign_ym DATE,
    position VARCHAR(50),
    detail TEXT,
    salary VARCHAR(50)
);

-- RESUME_TAG 테이블 (이력서-스킬 연결)
CREATE TABLE resume_tag (
    resume_tag_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    tag_id INT NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id) ON DELETE CASCADE
);

-- RESUME_TAG_COPY 테이블 (이력서-스킬 연결 사본)
CREATE TABLE resume_tag_copy (
    resume_tag_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    tag_id INT NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- CERTIFICATE_RESUME 테이블 (이력서 자격증)
CREATE TABLE certificate_resume (
    certificate_resume_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    certificate_name VARCHAR(100),
    acquisition_date DATE,
    issuing_authority VARCHAR(100),
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

-- CERTIFICATE_RESUME_COPY 테이블 (이력서 자격증 사본)
CREATE TABLE certificate_resume_copy (
    certificate_resume_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    certificate_name VARCHAR(100),
    acquisition_date DATE,
    issuing_authority VARCHAR(100)
);

-- PORTFOLIO 테이블 (포트폴리오)
CREATE TABLE portfolio (
    portfolio_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    file_name VARCHAR(200),
    stored_file_name VARCHAR(200),
    file_extension VARCHAR(10),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

-- PORTFOLIO_COPY 테이블 (포트폴리오 사본)
CREATE TABLE portfolio_copy (
    portfolio_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    file_name VARCHAR(200),
    stored_file_name VARCHAR(200),
    file_extension VARCHAR(10),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- RESUME_MANAGE 테이블 (지원관리)
CREATE TABLE resume_manage (
    rm_id INT NOT NULL,
    job_post_id INT NOT NULL,
    member_id INT NOT NULL,
    state_id INT DEFAULT 1,
    apply_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (rm_id, job_post_id),
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE,
    FOREIGN KEY (state_id) REFERENCES state(state_id)
);

-- BOOKMARK 테이블 (북마크)
CREATE TABLE bookMark (
    member_id INT NOT NULL,
    job_post_id INT NOT NULL,
    PRIMARY KEY (member_id, job_post_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE,
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE
);

-- NOTICE_CATEGORY 테이블 (공지사항 카테고리)
CREATE TABLE notice_category (
    notice_category_id INT AUTO_INCREMENT PRIMARY KEY,
    notice_category_content VARCHAR(50) NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- NOTICE 테이블 (공지사항)
CREATE TABLE notice (
    notice_id INT AUTO_INCREMENT PRIMARY KEY,
    admin_id VARCHAR(50) NOT NULL,
    notice_category_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    views INT DEFAULT 0,
    FOREIGN KEY (admin_id) REFERENCES admin(admin_id),
    FOREIGN KEY (notice_category_id) REFERENCES notice_category(notice_category_id)
);

-- COMMUNITY 테이블 (커뮤니티)
CREATE TABLE community (
    community_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    views INT DEFAULT 0,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

-- POST_COMMENT 테이블 (게시글 댓글)
CREATE TABLE post_comment (
    post_comment_id INT AUTO_INCREMENT PRIMARY KEY,
    community_id INT NOT NULL,
    member_id INT NOT NULL,
    content TEXT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (community_id) REFERENCES community(community_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

-- REPLY 테이블 (대댓글)
CREATE TABLE reply (
    reply_id INT AUTO_INCREMENT PRIMARY KEY,
    post_comment_id INT NOT NULL,
    member_id INT NOT NULL,
    comment_content TEXT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (post_comment_id) REFERENCES post_comment(post_comment_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

-- COMMUNITY_FILE 테이블 (커뮤니티 파일)
CREATE TABLE community_file (
    community_file_id INT AUTO_INCREMENT PRIMARY KEY,
    community_id INT NOT NULL,
    file_name VARCHAR(200),
    stored_file_name VARCHAR(200),
    file_size BIGINT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    file_extension VARCHAR(10),
    file_link VARCHAR(500),
    FOREIGN KEY (community_id) REFERENCES community(community_id) ON DELETE CASCADE
);

-- CHAT_ROOM 테이블 (채팅방)
CREATE TABLE chat_room (
    room_id VARCHAR(255) PRIMARY KEY,
    email VARCHAR(255),
    last_message_time TIMESTAMP(6),
    name VARCHAR(255)
);

-- CHAT_MESSAGE 테이블 (채팅 메시지)
CREATE TABLE chat_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id VARCHAR(255) NOT NULL,
    sender VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    send_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES chat_room(room_id) ON DELETE CASCADE
);

-- ===============================
-- 인덱스 생성
-- ===============================

CREATE INDEX idx_member_email ON member(email);
CREATE INDEX idx_company_email ON company(email);
CREATE INDEX idx_job_post_company ON job_post(company_id);
CREATE INDEX idx_job_post_dates ON job_post(start_date, end_date);
CREATE INDEX idx_job_post_views ON job_post(views);
CREATE INDEX idx_resume_member ON resume(member_id);
CREATE INDEX idx_community_member ON community(member_id);
CREATE INDEX idx_community_create_at ON community(create_at);
CREATE INDEX idx_job_post_tag_job ON job_post_tag(job_post);
CREATE INDEX idx_job_post_tag_tag ON job_post_tag(tag_id);
CREATE INDEX idx_tag_job_group ON tag(job_group_id);
CREATE INDEX idx_career_job ON career(job_id);
CREATE INDEX idx_career_job_group ON career(job_group_id);

-- ===============================
-- 기본 데이터 삽입
-- ===============================

-- STATE 데이터
INSERT INTO state (state_name) VALUES 
('지원완료'), ('서류합격'), ('면접진행'), ('최종합격'), ('불합격');

-- ADMIN 데이터  
INSERT INTO admin (admin_id, password) VALUES 
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.');

-- NOTICE_CATEGORY 데이터
INSERT INTO notice_category (notice_category_content) VALUES 
('일반공지'), ('시스템점검'), ('업데이트'), ('이벤트');

-- JOB_GROUP 데이터
INSERT INTO job_group (job_group_name) VALUES
('개발·프로그래밍'), ('기획·전략'), ('디자인'), ('마케팅·광고·MD'), ('영업'), ('고객서비스·리테일'),
('경영·비즈니스'), ('미디어·커뮤니케이션'), ('인사·HR'), ('회계·세무·재무'), ('총무·법무·사무'),
('제조·생산'), ('의료·바이오'), ('교육'), ('건설·시설'), ('운송·물류'), ('공공·복지');

-- JOB 데이터 (개발·프로그래밍)
INSERT INTO job (job_name, job_group_id) VALUES
('백엔드 개발자', 1), ('프론트엔드 개발자', 1), ('풀스택 개발자', 1), ('안드로이드 개발자', 1),
('iOS 개발자', 1), ('데이터 엔지니어', 1), ('머신러닝 엔지니어', 1), ('DevOps 엔지니어', 1),
('시스템 엔지니어', 1), ('보안 엔지니어', 1), ('QA 엔지니어', 1), ('게임 개발자', 1);

-- JOB 데이터 (기획·전략)
INSERT INTO job (job_name, job_group_id) VALUES
('서비스 기획자', 2), ('상품 기획자', 2), ('사업 기획자', 2), ('전략 기획자', 2),
('데이터 분석가', 2), ('비즈니스 분석가', 2), ('프로덕트 매니저', 2), ('프로젝트 매니저', 2);

-- JOB 데이터 (디자인)
INSERT INTO job (job_name, job_group_id) VALUES
('UI/UX 디자이너', 3), ('웹 디자이너', 3), ('그래픽 디자이너', 3), ('브랜드 디자이너', 3),
('제품 디자이너', 3), ('패션 디자이너', 3), ('영상 디자이너', 3), ('게임 디자이너', 3);

-- TAG 데이터 (개발 스킬)
INSERT INTO tag (tag_name, job_group_id, create_at, modified_at) VALUES
('Java', 1, NOW(), NOW()), ('Spring', 1, NOW(), NOW()), ('JavaScript', 1, NOW(), NOW()),
('React', 1, NOW(), NOW()), ('Vue.js', 1, NOW(), NOW()), ('Node.js', 1, NOW(), NOW()),
('Python', 1, NOW(), NOW()), ('Django', 1, NOW(), NOW()), ('Flask', 1, NOW(), NOW()),
('PHP', 1, NOW(), NOW()), ('Laravel', 1, NOW(), NOW()), ('C#', 1, NOW(), NOW()),
('.NET', 1, NOW(), NOW()), ('Go', 1, NOW(), NOW()), ('Kotlin', 1, NOW(), NOW()),
('Swift', 1, NOW(), NOW()), ('MySQL', 1, NOW(), NOW()), ('PostgreSQL', 1, NOW(), NOW()),
('Oracle', 1, NOW(), NOW()), ('MongoDB', 1, NOW(), NOW()), ('Redis', 1, NOW(), NOW()),
('Docker', 1, NOW(), NOW()), ('Kubernetes', 1, NOW(), NOW()), ('AWS', 1, NOW(), NOW()),
('Azure', 1, NOW(), NOW()), ('GCP', 1, NOW(), NOW()), ('Jenkins', 1, NOW(), NOW()),
('Git', 1, NOW(), NOW()), ('Linux', 1, NOW(), NOW()), ('Apache', 1, NOW(), NOW()),
('Nginx', 1, NOW(), NOW()), ('Elasticsearch', 1, NOW(), NOW()), ('Kafka', 1, NOW(), NOW()),
('TensorFlow', 1, NOW(), NOW()), ('PyTorch', 1, NOW(), NOW()), ('Hadoop', 1, NOW(), NOW()),
('Spark', 1, NOW(), NOW()), ('Tableau', 1, NOW(), NOW()), ('Power BI', 1, NOW(), NOW()),
('Unity', 1, NOW(), NOW()), ('Unreal Engine', 1, NOW(), NOW());

-- TAG 데이터 (기획 스킬)
INSERT INTO tag (tag_name, job_group_id, create_at, modified_at) VALUES
('기획', 2, NOW(), NOW()), ('전략수립', 2, NOW(), NOW()), ('비즈니스모델', 2, NOW(), NOW()),
('시장조사', 2, NOW(), NOW()), ('데이터분석', 2, NOW(), NOW()), ('SQL', 2, NOW(), NOW()),
('Excel', 2, NOW(), NOW()), ('PowerPoint', 2, NOW(), NOW()), ('Figma', 2, NOW(), NOW()),
('Sketch', 2, NOW(), NOW()), ('Adobe XD', 2, NOW(), NOW()), ('프로토타이핑', 2, NOW(), NOW()),
('사용자조사', 2, NOW(), NOW()), ('와이어프레임', 2, NOW(), NOW()), ('스토리보드', 2, NOW(), NOW());

-- TAG 데이터 (디자인 스킬)
INSERT INTO tag (tag_name, job_group_id, create_at, modified_at) VALUES
('Photoshop', 3, NOW(), NOW()), ('Illustrator', 3, NOW(), NOW()), ('After Effects', 3, NOW(), NOW()),
('Premiere Pro', 3, NOW(), NOW()), ('InDesign', 3, NOW(), NOW()), ('XD', 3, NOW(), NOW()),
('Figma', 3, NOW(), NOW()), ('Sketch', 3, NOW(), NOW()), ('Principle', 3, NOW(), NOW()),
('Zeplin', 3, NOW(), NOW()), ('InVision', 3, NOW(), NOW()), ('Marvel', 3, NOW(), NOW()),
('Axure', 3, NOW(), NOW()), ('Balsamiq', 3, NOW(), NOW()), ('웹디자인', 3, NOW(), NOW()),
('모바일디자인', 3, NOW(), NOW()), ('반응형디자인', 3, NOW(), NOW()), ('타이포그래피', 3, NOW(), NOW()),
('컬러디자인', 3, NOW(), NOW()), ('레이아웃', 3, NOW(), NOW());

-- 샘플 MEMBER 데이터
INSERT INTO member (email, password, nickname, resume_max, provider) VALUES
('user1@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '개발자김철수', 5, 'normal'),
('user2@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '디자이너박영희', 5, 'normal'),
('user3@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '기획자이민수', 5, 'normal'),
('google@gmail.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '구글사용자', 5, 'google');

-- 샘플 COMPANY 데이터
INSERT INTO company (email, password, company_name, brn, representative, tel, address) VALUES
('samsung@company.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '삼성전자', '124-81-12345', '김지훈', '02-3456-7890', '서울 강남구 테헤란로 123'),
('naver@company.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '네이버', '220-88-12345', '오하윤', '031-3456-7892', '경기 성남시 판교로 235'),
('kakao@company.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '카카오', '784-86-12345', '홍서진', '031-9876-5432', '경기 성남시 판교로 456'),
('startup@company.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '스타트업코리아', '123-45-67890', '최민준', '02-1234-5678', '서울 강남구 역삼로 100');

-- 샘플 JOB_POST 데이터
INSERT INTO job_post (company_id, title, content, salary, work_hours, job_id, job_group_id, job_img, career_type, education, template_type, start_date, end_date) VALUES
(1, 'Spring Boot 백엔드 개발자 모집', '삼성전자에서 Spring Boot를 활용한 백엔드 개발자를 모집합니다.', '연봉 4000만원~6000만원', '주 5일 09:00~18:00', 1, 1, '/images/samsung_job.jpg', '경력3년이상', '대졸이상', 1, '2024-01-01', '2024-12-31'),
(2, '네이버 프론트엔드 개발자 (React)', '네이버에서 React 기반 프론트엔드 개발자를 채용합니다.', '연봉 4500만원~7000만원', '주 5일 10:00~19:00', 2, 1, '/images/naver_job.jpg', '경력2년이상', '대졸이상', 1, '2024-01-01', '2024-12-31'),
(3, 'UI/UX 디자이너 채용', '카카오에서 모바일 앱 UI/UX 디자이너를 모집합니다.', '연봉 3500만원~5500만원', '주 5일 09:00~18:00', 17, 3, '/images/kakao_job.jpg', '경력1년이상', '대졸이상', 1, '2024-01-01', '2024-12-31'),
(4, '스타트업 풀스택 개발자', '빠르게 성장하는 스타트업에서 풀스택 개발자를 찾습니다.', '연봉 3000만원~5000만원', '주 5일 09:00~18:00', 3, 1, '/images/startup_job.jpg', '경력무관', '학력무관', 1, '2024-01-01', '2024-12-31');

-- JOB_POST_TAG 연결 데이터
INSERT INTO job_post_tag (job_post, tag_id) VALUES
(1, 1), (1, 2), (1, 17), -- Java, Spring, MySQL
(2, 3), (2, 4), (2, 5), -- JavaScript, React, Vue.js  
(3, 9), (3, 10), (3, 11), -- Figma, Sketch, Adobe XD
(4, 1), (4, 3), (4, 4); -- Java, JavaScript, React

-- JOB_POST_WELFARE 데이터
INSERT INTO job_post_welfare (job_post_id, benefit_text) VALUES
(1, '4대보험 완비'), (1, '연차 15일'), (1, '점심 제공'), (1, '야근수당'),
(2, '4대보험 완비'), (2, '연차 20일'), (2, '간식 무제한'), (2, '재택근무 가능'),
(3, '4대보험 완비'), (3, '연차 15일'), (3, '교육비 지원'), (3, '건강검진'),
(4, '4대보험 완비'), (4, '연차 12일'), (4, '스톡옵션'), (4, '자율출퇴근');

-- 샘플 NOTICE 데이터
INSERT INTO notice (admin_id, notice_category_id, title, content, views) VALUES
('admin', 1, '사이트 오픈 안내', 'JobFinder 사이트가 정식 오픈하였습니다.', 150),
('admin', 2, '정기 서버 점검 안내', '매주 일요일 새벽 2시~4시 서버 점검이 있습니다.', 75),
('admin', 3, '모바일 앱 출시 예정', '곧 모바일 앱이 출시될 예정입니다.', 200),
('admin', 4, '회원가입 이벤트', '신규 회원가입시 추가 혜택을 드립니다.', 120);

-- 샘플 COMMUNITY 데이터  
INSERT INTO community (member_id, title, content, views) VALUES
(1, 'Spring Boot 학습 후기', 'Spring Boot를 처음 배우면서 느낀 점들을 공유합니다.', 45),
(2, 'UI/UX 트렌드 2024', '2024년 UI/UX 디자인 트렌드에 대해 이야기해봐요.', 62),
(3, '취업 준비 노하우', '면접 준비할 때 도움이 되는 팁들을 공유합니다.', 88),
(1, 'MySQL vs PostgreSQL', '데이터베이스 선택 기준에 대해 토론해봅시다.', 34);

-- 샘플 POST_COMMENT 데이터
INSERT INTO post_comment (community_id, member_id, content) VALUES
(1, 2, '정말 유용한 정보네요! 감사합니다.'),
(1, 3, '저도 비슷한 경험이 있어서 공감됩니다.'),
(2, 1, '최신 트렌드 정보 감사해요.'),
(3, 2, '면접 팁 정말 도움됐습니다!');

-- 샘플 REPLY 데이터
INSERT INTO reply (post_comment_id, member_id, comment_content) VALUES
(1, 1, '도움이 되었다니 다행입니다!'),
(2, 1, '네, 함께 공부해요!'),
(3, 2, '더 좋은 정보 찾아서 공유할게요.'),
(4, 3, '취업 성공하시길 바랍니다!');

-- ===============================
-- 마이그레이션 완료 메시지
-- ===============================

SELECT 'MySQL 마이그레이션이 성공적으로 완료되었습니다!' as message;
SELECT 'Oracle 11g -> MySQL 8.0 변환 완료' as status;
SELECT COUNT(*) as total_tables FROM information_schema.tables WHERE table_schema = 'jobfinder'; 