-- 테이블 생성 DDL - MySQL 버전
-- Oracle 11g -> MySQL 8.0 변환

USE jobfinder;

-- 기존 테이블 삭제
SET foreign_key_checks = 0;

-- chat_room
DROP TABLE IF EXISTS chat_room;
CREATE TABLE chat_room (
    room_id VARCHAR(255) NOT NULL PRIMARY KEY,
    email VARCHAR(255),
    last_message_time TIMESTAMP(6),
    name VARCHAR(255)
);

-- chat_message
DROP TABLE IF EXISTS chat_message;
CREATE TABLE chat_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id VARCHAR(255) NOT NULL,
    sender VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    send_time DATETIME NOT NULL,
    FOREIGN KEY (room_id) REFERENCES chat_room(room_id) ON DELETE CASCADE
);

-- job_group
DROP TABLE IF EXISTS job_group;
CREATE TABLE job_group (
    job_group_id INT AUTO_INCREMENT PRIMARY KEY,
    job_group_name VARCHAR(300)
);

-- job
DROP TABLE IF EXISTS job;
CREATE TABLE job (
    job_id INT AUTO_INCREMENT PRIMARY KEY,
    job_group_id INT,
    job_name VARCHAR(500),
    FOREIGN KEY (job_group_id) REFERENCES job_group(job_group_id) ON DELETE CASCADE
);

-- certificate (자격증)
DROP TABLE IF EXISTS certificate;
CREATE TABLE certificate (
    certificate_id INT AUTO_INCREMENT PRIMARY KEY,
    certificate_name VARCHAR(500),
    issuing_authority VARCHAR(500),
    is_active TINYINT DEFAULT 1
);

-- tag
DROP TABLE IF EXISTS tag;
CREATE TABLE tag (
    tag_id INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(500),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    job_group_id INT,
    FOREIGN KEY (job_group_id) REFERENCES job_group(job_group_id) ON DELETE CASCADE
);

-- job_post_welfare (복지)
DROP TABLE IF EXISTS job_post_welfare;
CREATE TABLE job_post_welfare (
    job_welfare_id INT AUTO_INCREMENT PRIMARY KEY,
    job_post_id INT NOT NULL,
    benefit_text VARCHAR(500) NOT NULL,
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE
);

-- state
DROP TABLE IF EXISTS state;
CREATE TABLE state (
    state_id INT AUTO_INCREMENT PRIMARY KEY,
    state_name VARCHAR(100)
);

-- member
DROP TABLE IF EXISTS member;
CREATE TABLE member (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    nickname VARCHAR(100),
    resume_max INT DEFAULT 5,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    provider VARCHAR(50) DEFAULT 'normal'
);

-- admin
DROP TABLE IF EXISTS admin;
CREATE TABLE admin (
    admin_id VARCHAR(100) PRIMARY KEY,
    password VARCHAR(100)
);

-- company
DROP TABLE IF EXISTS company;
CREATE TABLE company (
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100),
    password VARCHAR(100),
    company_name VARCHAR(200),
    brn VARCHAR(50),
    representative VARCHAR(100),
    tel VARCHAR(50),
    postal_code_id INT,
    address VARCHAR(300),
    arena_name VARCHAR(200),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- profile_temp
DROP TABLE IF EXISTS profile_temp;
CREATE TABLE profile_temp (
    upload_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT,
    file_name VARCHAR(500),
    is_used TINYINT DEFAULT 0,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

-- resume
DROP TABLE IF EXISTS resume;
CREATE TABLE resume (
    resume_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_title VARCHAR(200),
    name VARCHAR(100),
    birthdate DATE,
    phonenumber VARCHAR(50),
    email VARCHAR(100),
    postal_code_id INT,
    address VARCHAR(300),
    self_introduction TEXT,
    member_id INT,
    profile VARCHAR(500),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_public TINYINT DEFAULT 0,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

-- certificate_resume (이력서 자격증)
DROP TABLE IF EXISTS certificate_resume;
CREATE TABLE certificate_resume (
    certificate_resume_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT,
    certificate_name VARCHAR(200),
    acquisition_date DATE,
    issuing_authority VARCHAR(200),
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

-- career
DROP TABLE IF EXISTS career;
CREATE TABLE career (
    career_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT,
    company_name VARCHAR(200),
    department_name VARCHAR(100),
    hire_ym DATE,
    resign_ym DATE,
    position VARCHAR(100),
    job_id INT,
    job_group_id INT,
    salary VARCHAR(100),
    detail TEXT,
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE,
    FOREIGN KEY (job_id) REFERENCES job(job_id),
    FOREIGN KEY (job_group_id) REFERENCES job_group(job_group_id)
);

-- education
DROP TABLE IF EXISTS education;
CREATE TABLE education (
    edu_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT,
    edu_institution VARCHAR(200),
    edu_name VARCHAR(200),
    start_date DATE,
    end_date DATE,
    content TEXT,
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

-- school
DROP TABLE IF EXISTS school;
CREATE TABLE school (
    school_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT,
    sortation VARCHAR(50),
    school_name VARCHAR(200),
    year_of_graduation VARCHAR(10),
    status VARCHAR(50),
    major_name VARCHAR(100),
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

-- portfolio
DROP TABLE IF EXISTS portfolio;
CREATE TABLE portfolio (
    portfolio_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT,
    file_name VARCHAR(300),
    stored_file_name VARCHAR(300),
    file_extension VARCHAR(20),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE
);

-- job_post
DROP TABLE IF EXISTS job_post;
CREATE TABLE job_post (
    job_post_id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT,
    title VARCHAR(300),
    content TEXT,
    salary VARCHAR(100),
    work_hours VARCHAR(100),
    job_id INT,
    job_group_id INT,
    views INT DEFAULT 0,
    job_img VARCHAR(500),
    career_type VARCHAR(50),
    education VARCHAR(50),
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

-- job_post_file
DROP TABLE IF EXISTS job_post_file;
CREATE TABLE job_post_file (
    job_post_file_id INT AUTO_INCREMENT PRIMARY KEY,
    job_post_id INT,
    file_name VARCHAR(300),
    stored_file_name VARCHAR(300),
    file_size BIGINT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    file_extension VARCHAR(20),
    file_link VARCHAR(500),
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE
);

-- job_post_tag
DROP TABLE IF EXISTS job_post_tag;
CREATE TABLE job_post_tag (
    job_post INT,
    tag_id INT,
    PRIMARY KEY (job_post, tag_id),
    FOREIGN KEY (job_post) REFERENCES job_post(job_post_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id) ON DELETE CASCADE
);

-- job_post_question
DROP TABLE IF EXISTS job_post_question;
CREATE TABLE job_post_question (
    job_post_question_id INT AUTO_INCREMENT PRIMARY KEY,
    job_post_id INT,
    question_text TEXT,
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE
);

-- job_post_answer
DROP TABLE IF EXISTS job_post_answer;
CREATE TABLE job_post_answer (
    job_post_answer_id INT AUTO_INCREMENT PRIMARY KEY,
    job_post_question_id INT,
    member_id INT,
    answer_text TEXT,
    answer_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_post_question_id) REFERENCES job_post_question(job_post_question_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

-- resume_manage
DROP TABLE IF EXISTS resume_manage;
CREATE TABLE resume_manage (
    rm_id INT,
    job_post_id INT,
    member_id INT,
    state_id INT DEFAULT 1,
    apply_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (rm_id, job_post_id),
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE,
    FOREIGN KEY (state_id) REFERENCES state(state_id)
);

-- resume_tag
DROP TABLE IF EXISTS resume_tag;
CREATE TABLE resume_tag (
    resume_tag_id INT AUTO_INCREMENT PRIMARY KEY,
    resume_id INT,
    tag_id INT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (resume_id) REFERENCES resume(resume_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id) ON DELETE CASCADE
);

-- bookmark
DROP TABLE IF EXISTS bookMark;
CREATE TABLE bookMark (
    member_id INT,
    job_post_id INT,
    PRIMARY KEY (member_id, job_post_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE,
    FOREIGN KEY (job_post_id) REFERENCES job_post(job_post_id) ON DELETE CASCADE
);

-- community
DROP TABLE IF EXISTS community;
CREATE TABLE community (
    community_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT,
    title VARCHAR(300),
    content TEXT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    views INT DEFAULT 0,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

-- community_file
DROP TABLE IF EXISTS community_file;
CREATE TABLE community_file (
    community_file_id INT AUTO_INCREMENT PRIMARY KEY,
    community_id INT,
    file_name VARCHAR(300),
    stored_file_name VARCHAR(300),
    file_size BIGINT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    file_extension VARCHAR(20),
    file_link VARCHAR(500),
    FOREIGN KEY (community_id) REFERENCES community(community_id) ON DELETE CASCADE
);

-- post_comment
DROP TABLE IF EXISTS post_comment;
CREATE TABLE post_comment (
    post_comment_id INT AUTO_INCREMENT PRIMARY KEY,
    community_id INT,
    member_id INT,
    content TEXT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (community_id) REFERENCES community(community_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

-- reply
DROP TABLE IF EXISTS reply;
CREATE TABLE reply (
    reply_id INT AUTO_INCREMENT PRIMARY KEY,
    post_comment_id INT,
    member_id INT,
    comment_content TEXT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (post_comment_id) REFERENCES post_comment(post_comment_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

-- notice_category
DROP TABLE IF EXISTS notice_category;
CREATE TABLE notice_category (
    notice_category_id INT AUTO_INCREMENT PRIMARY KEY,
    notice_category_content VARCHAR(100),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- notice
DROP TABLE IF EXISTS notice;
CREATE TABLE notice (
    notice_id INT AUTO_INCREMENT PRIMARY KEY,
    admin_id VARCHAR(100),
    notice_category_id INT,
    title VARCHAR(300),
    content TEXT,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    views INT DEFAULT 0,
    FOREIGN KEY (admin_id) REFERENCES admin(admin_id),
    FOREIGN KEY (notice_category_id) REFERENCES notice_category(notice_category_id)
);

SET foreign_key_checks = 1;

-- 인덱스 생성
CREATE INDEX idx_member_email ON member(email);
CREATE INDEX idx_company_email ON company(email);
CREATE INDEX idx_job_post_company ON job_post(company_id);
CREATE INDEX idx_resume_member ON resume(member_id);
CREATE INDEX idx_community_member ON community(member_id);
CREATE INDEX idx_job_post_tag_job ON job_post_tag(job_post);
CREATE INDEX idx_job_post_tag_tag ON job_post_tag(tag_id);

SELECT 'MySQL 테이블 DDL 변환 완료!' as message; 