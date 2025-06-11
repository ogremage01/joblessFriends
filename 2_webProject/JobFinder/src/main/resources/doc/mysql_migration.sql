-- JobFinder MySQL 데이터베이스 생성 스크립트
-- Oracle 11g에서 MySQL 8.0으로 마이그레이션

-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS jobfinder 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE jobfinder;

-- MEMBER 테이블
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

-- COMPANY 테이블  
CREATE TABLE company (
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    brn VARCHAR(20) NOT NULL,
    representative VARCHAR(50) NOT NULL,
    tel VARCHAR(20),
    address VARCHAR(200),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- JOB_GROUP 테이블
CREATE TABLE job_group (
    job_group_id INT AUTO_INCREMENT PRIMARY KEY,
    job_group_name VARCHAR(50) NOT NULL
);

-- JOB 테이블
CREATE TABLE job (
    job_id INT AUTO_INCREMENT PRIMARY KEY,
    job_name VARCHAR(50) NOT NULL,
    job_group_id INT,
    FOREIGN KEY (job_group_id) REFERENCES job_group(job_group_id)
);

-- TAG 테이블 (스킬)
CREATE TABLE tag (
    tag_id INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    job_group_id INT,
    FOREIGN KEY (job_group_id) REFERENCES job_group(job_group_id)
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
    FOREIGN KEY (company_id) REFERENCES company(company_id),
    FOREIGN KEY (job_id) REFERENCES job(job_id),
    FOREIGN KEY (job_group_id) REFERENCES job_group(job_group_id)
);

-- RESUME 테이블 (이력서)
CREATE TABLE resume (
    resume_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_title VARCHAR(100),
    member_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    birthdate DATE,
    phonenumber VARCHAR(20),
    email VARCHAR(100),
    postal_code_id INT,
    address VARCHAR(200),
    self_introduction TEXT,
    profile VARCHAR(500),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member(member_id)
);

-- RESUME_COPY 테이블 (이력서 사본)
CREATE TABLE resume_copy (
    resume_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_title VARCHAR(100),
    member_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    birthdate DATE,
    phonenumber VARCHAR(20),
    email VARCHAR(100),
    postal_code_id INT,
    address VARCHAR(200),
    self_introduction TEXT,
    profile VARCHAR(500),
    match_score DECIMAL(5,2),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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

-- SCHOOL_COPY 테이블
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

-- EDUCATION_COPY 테이블
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
    job_group_id INT,
    job_id INT,
    detail TEXT,
    salary VARCHAR(50),
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

-- CAREER_COPY 테이블
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
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id)
);

-- RESUME_TAG_COPY 테이블
CREATE TABLE resume_tag_copy (
    resume_tag_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    tag_id INT NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- JOB_POST_TAG 테이블 (채용공고-스킬 연결)
CREATE TABLE job_post_tag (
    job_post INT NOT NULL,
    tag_id INT NOT NULL,
    PRIMARY KEY (job_post, tag_id),
    FOREIGN KEY (job_post) REFERENCES job_post(job_post_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id)
);

-- STATE 테이블 (지원상태)
CREATE TABLE state (
    state_id INT AUTO_INCREMENT PRIMARY KEY,
    state_name VARCHAR(20) NOT NULL
);

-- RESUME_MANAGE 테이블 (지원관리)
CREATE TABLE resume_manage (
    rm_id INT NOT NULL,
    job_post_id INT NOT NULL,
    member_id INT NOT NULL,
    state_id INT DEFAULT 1,
    apply_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (rm_id, job_post_id),
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id),
    FOREIGN KEY (state_id) REFERENCES state(state_id)
);

-- CERTIFICATE_RESUME 테이블 (자격증)
CREATE TABLE certificate_resume (
    certificate_resume_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    certificate_name VARCHAR(100),
    acquisition_date DATE,
    issuing_authority VARCHAR(100),
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

-- CERTIFICATE_RESUME_COPY 테이블
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

-- PORTFOLIO_COPY 테이블
CREATE TABLE portfolio_copy (
    portfolio_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    file_name VARCHAR(200),
    stored_file_name VARCHAR(200),
    file_extension VARCHAR(10),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    FOREIGN KEY (member_id) REFERENCES member(member_id)
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
    FOREIGN KEY (member_id) REFERENCES member(member_id)
);

-- REPLY 테이블 (댓글의 대댓글)
CREATE TABLE reply (
    reply_id INT AUTO_INCREMENT PRIMARY KEY,
    post_comment_id INT NOT NULL,
    member_id INT NOT NULL,
    comment_content TEXT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (post_comment_id) REFERENCES post_comment(post_comment_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(member_id)
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
    admin_id INT NOT NULL,
    notice_category_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    views INT DEFAULT 0,
    FOREIGN KEY (notice_category_id) REFERENCES notice_category(notice_category_id)
);

-- JOB_POST_WELFARE 테이블 (복리후생)
CREATE TABLE job_post_welfare (
    job_welfare_id INT AUTO_INCREMENT PRIMARY KEY,
    job_post_id INT NOT NULL,
    benefit_text VARCHAR(200),
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE
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
    FOREIGN KEY (member_id) REFERENCES member(member_id)
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

-- 채팅 관련 테이블들
CREATE TABLE chat_room (
    room_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_name VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT
);

CREATE TABLE chat_message (
    message_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    message_type VARCHAR(50) DEFAULT 'TEXT',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES chat_room(room_id) ON DELETE CASCADE
);

-- 북마크 테이블
CREATE TABLE bookMark (
    member_id INT NOT NULL,
    job_post_id INT NOT NULL,
    PRIMARY KEY (member_id, job_post_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE,
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE
);

-- 인덱스 생성
CREATE INDEX idx_member_email ON member(email);
CREATE INDEX idx_company_email ON company(email);
CREATE INDEX idx_job_post_company ON job_post(company_id);
CREATE INDEX idx_job_post_dates ON job_post(start_date, end_date);
CREATE INDEX idx_resume_member ON resume(member_id);
CREATE INDEX idx_community_member ON community(member_id);
CREATE INDEX idx_job_post_tag_job ON job_post_tag(job_post);
CREATE INDEX idx_job_post_tag_tag ON job_post_tag(tag_id);

-- 기본 데이터 삽입
INSERT INTO state (state_name) VALUES 
('지원완료'), ('서류합격'), ('면접진행'), ('최종합격'), ('불합격');

INSERT INTO notice_category (notice_category_content) VALUES 
('일반공지'), ('시스템점검'), ('업데이트'), ('이벤트'); 