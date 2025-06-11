-- JobFinder MySQL 데이터베이스 스크립트
-- Oracle 11g -> MySQL 8.0 변환

-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS jobfinder 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE jobfinder;

-- 기존 테이블 삭제
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

-- 기본 테이블 생성
CREATE TABLE state (
    state_id INT AUTO_INCREMENT PRIMARY KEY,
    state_name VARCHAR(50) NOT NULL
);

CREATE TABLE admin (
    admin_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL
);

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

CREATE TABLE job_group (
    job_group_id INT AUTO_INCREMENT PRIMARY KEY,
    job_group_name VARCHAR(50) NOT NULL
);

CREATE TABLE job (
    job_id INT AUTO_INCREMENT PRIMARY KEY,
    job_group_id INT,
    job_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (job_group_id) REFERENCES job_group(job_group_id) ON DELETE CASCADE
);

CREATE TABLE certificate (
    certificate_id INT AUTO_INCREMENT PRIMARY KEY,
    certificate_name VARCHAR(100) NOT NULL,
    issuing_authority VARCHAR(100),
    is_active TINYINT DEFAULT 1
);

CREATE TABLE tag (
    tag_id INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(50) NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    job_group_id INT,
    FOREIGN KEY (job_group_id) REFERENCES job_group(job_group_id) ON DELETE CASCADE
);

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

CREATE TABLE job_post_welfare (
    job_welfare_id INT AUTO_INCREMENT PRIMARY KEY,
    job_post_id INT NOT NULL,
    benefit_text VARCHAR(500) NOT NULL,
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE
);

CREATE TABLE job_post_tag (
    job_post INT NOT NULL,
    tag_id INT NOT NULL,
    PRIMARY KEY (job_post, tag_id),
    FOREIGN KEY (job_post) REFERENCES job_post(job_post_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id) ON DELETE CASCADE
);

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

CREATE TABLE job_post_question (
    job_post_question_id INT AUTO_INCREMENT PRIMARY KEY,
    job_post_id INT NOT NULL,
    question_text TEXT,
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE
);

CREATE TABLE job_post_answer (
    job_post_answer_id INT AUTO_INCREMENT PRIMARY KEY,
    job_post_question_id INT NOT NULL,
    member_id INT NOT NULL,
    answer_text TEXT,
    answer_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_post_question_id) REFERENCES job_post_question(job_post_question_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

CREATE TABLE profile_temp (
    upload_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT,
    file_name VARCHAR(200),
    is_used TINYINT DEFAULT 0,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

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

CREATE TABLE education_copy (
    edu_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    edu_institution VARCHAR(100),
    edu_name VARCHAR(100),
    start_date DATE,
    end_date DATE,
    content TEXT
);

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

CREATE TABLE resume_tag (
    resume_tag_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    tag_id INT NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id) ON DELETE CASCADE
);

CREATE TABLE resume_tag_copy (
    resume_tag_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    tag_id INT NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE certificate_resume (
    certificate_resume_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    certificate_name VARCHAR(100),
    acquisition_date DATE,
    issuing_authority VARCHAR(100),
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

CREATE TABLE certificate_resume_copy (
    certificate_resume_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    certificate_name VARCHAR(100),
    acquisition_date DATE,
    issuing_authority VARCHAR(100)
);

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

CREATE TABLE portfolio_copy (
    portfolio_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT NOT NULL,
    file_name VARCHAR(200),
    stored_file_name VARCHAR(200),
    file_extension VARCHAR(10),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

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

CREATE TABLE bookMark (
    member_id INT NOT NULL,
    job_post_id INT NOT NULL,
    PRIMARY KEY (member_id, job_post_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE,
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE
);

CREATE TABLE notice_category (
    notice_category_id INT AUTO_INCREMENT PRIMARY KEY,
    notice_category_content VARCHAR(50) NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

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

CREATE TABLE chat_room (
    room_id VARCHAR(255) PRIMARY KEY,
    email VARCHAR(255),
    last_message_time TIMESTAMP(6),
    name VARCHAR(255)
);

CREATE TABLE chat_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id VARCHAR(255) NOT NULL,
    sender VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    send_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES chat_room(room_id) ON DELETE CASCADE
);

-- 인덱스 생성
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

-- 기본 데이터 삽입
INSERT INTO state (state_name) VALUES 
('지원완료'), ('서류합격'), ('면접진행'), ('최종합격'), ('불합격');

INSERT INTO admin (admin_id, password) VALUES 
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.');

INSERT INTO notice_category (notice_category_content) VALUES 
('일반공지'), ('시스템점검'), ('업데이트'), ('이벤트');

SELECT 'MySQL 마이그레이션 완료!' as message; 