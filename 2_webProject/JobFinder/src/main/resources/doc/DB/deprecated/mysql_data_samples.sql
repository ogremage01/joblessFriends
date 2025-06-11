-- JobFinder MySQL 샘플 데이터
-- Oracle INSERT 문을 MySQL 형식으로 변환

USE jobfinder;

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

-- JOB 데이터 (마케팅·광고·MD)
INSERT INTO job (job_name, job_group_id) VALUES
('마케팅 전문가', 4), ('디지털 마케터', 4), ('퍼포먼스 마케터', 4), ('콘텐츠 마케터', 4),
('브랜드 매니저', 4), ('광고 기획자', 4), ('카피라이터', 4), ('MD(상품기획)', 4);

-- JOB 데이터 (영업)
INSERT INTO job (job_name, job_group_id) VALUES
('영업 매니저', 5), ('기술영업', 5), ('해외영업', 5), ('영업 기획', 5),
('세일즈 엔지니어', 5), ('영업 관리', 5), ('대리점 관리', 5), ('고객관리', 5);

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

-- TAG 데이터 (마케팅 스킬)
INSERT INTO tag (tag_name, job_group_id, create_at, modified_at) VALUES
('마케팅', 4, NOW(), NOW()), ('디지털마케팅', 4, NOW(), NOW()), ('퍼포먼스마케팅', 4, NOW(), NOW()),
('콘텐츠마케팅', 4, NOW(), NOW()), ('SNS마케팅', 4, NOW(), NOW()), ('브랜딩', 4, NOW(), NOW()),
('Google Analytics', 4, NOW(), NOW()), ('Facebook Ads', 4, NOW(), NOW()), ('Google Ads', 4, NOW(), NOW()),
('SEO', 4, NOW(), NOW()), ('SEM', 4, NOW(), NOW()), ('이메일마케팅', 4, NOW(), NOW()),
('마케팅자동화', 4, NOW(), NOW()), ('CRM', 4, NOW(), NOW()), ('데이터드리븐마케팅', 4, NOW(), NOW());

-- TAG 데이터 (영업 스킬)
INSERT INTO tag (tag_name, job_group_id, create_at, modified_at) VALUES
('영업', 5, NOW(), NOW()), ('B2B영업', 5, NOW(), NOW()), ('B2C영업', 5, NOW(), NOW()),
('기술영업', 5, NOW(), NOW()), ('해외영업', 5, NOW(), NOW()), ('세일즈', 5, NOW(), NOW()),
('CRM', 5, NOW(), NOW()), ('협상', 5, NOW(), NOW()), ('프레젠테이션', 5, NOW(), NOW()),
('고객관리', 5, NOW(), NOW()), ('영업전략', 5, NOW(), NOW()), ('계약관리', 5, NOW(), NOW()),
('파트너십', 5, NOW(), NOW()), ('채널관리', 5, NOW(), NOW()), ('영업분석', 5, NOW(), NOW());

-- 샘플 MEMBER 데이터
INSERT INTO member (email, password, nickname, resume_max, provider) VALUES
('user1@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '개발자김철수', 5, 'normal'),
('user2@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '디자이너박영희', 5, 'normal'),
('user3@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '기획자이민수', 5, 'normal'),
('user4@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '마케터최지우', 5, 'normal'),
('user5@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '영업팀장정수민', 5, 'normal'),
('google@gmail.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '구글사용자', 5, 'google'),
('kakao@kakao.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '카카오사용자', 5, 'kakao');

-- 샘플 COMPANY 데이터
INSERT INTO company (email, password, company_name, brn, representative, tel, address) VALUES
('samsung@company.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '삼성전자', '124-81-12345', '김지훈', '02-3456-7890', '서울 강남구 테헤란로 123'),
('naver@company.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '네이버', '220-88-12345', '오하윤', '031-3456-7892', '경기 성남시 판교로 235'),
('kakao@company.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '카카오', '784-86-12345', '홍서진', '031-9876-5432', '경기 성남시 판교로 456'),
('coupang@company.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '쿠팡', '120-88-00767', '김범석', '02-6713-8100', '서울 송파구 송파대로 570'),
('baemin@company.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '우아한형제들', '120-87-65763', '김봉진', '02-6956-1000', '서울 송파구 위례성대로 2'),
('ncsoft@company.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '엔씨소프트', '144-81-47434', '김택진', '031-8023-0001', '경기 성남시 분당구 대왕판교로 644'),
('startup@company.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '스타트업코리아', '123-45-67890', '최민준', '02-1234-5678', '서울 강남구 역삼로 100');

-- 샘플 JOB_POST 데이터
INSERT INTO job_post (company_id, title, content, salary, work_hours, job_id, job_group_id, job_img, career_type, education, template_type, start_date, end_date) VALUES
(1, 'Spring Boot 백엔드 개발자 모집', '삼성전자에서 Spring Boot를 활용한 백엔드 개발자를 모집합니다. Java, Spring Framework 경험이 있으신 분을 찾습니다.', '연봉 4000만원~6000만원', '주 5일 09:00~18:00', 1, 1, '/images/samsung_job.jpg', '경력3년이상', '대졸이상', 1, '2024-01-01', '2024-12-31'),
(2, '네이버 프론트엔드 개발자 (React)', '네이버에서 React 기반 프론트엔드 개발자를 채용합니다. 사용자 경험을 중시하는 서비스 개발에 참여하실 분을 모집합니다.', '연봉 4500만원~7000만원', '주 5일 10:00~19:00', 2, 1, '/images/naver_job.jpg', '경력2년이상', '대졸이상', 1, '2024-01-01', '2024-12-31'),
(3, 'UI/UX 디자이너 채용', '카카오에서 모바일 앱 UI/UX 디자이너를 모집합니다. 사용자 중심의 디자인 사고를 가지신 분을 찾습니다.', '연봉 3500만원~5500만원', '주 5일 09:00~18:00', 17, 3, '/images/kakao_job.jpg', '경력1년이상', '대졸이상', 1, '2024-01-01', '2024-12-31'),
(4, '쿠팡 데이터 엔지니어', '쿠팡에서 대용량 데이터 처리 및 분석 시스템을 구축할 데이터 엔지니어를 모집합니다.', '연봉 5000만원~8000만원', '주 5일 09:00~18:00', 6, 1, '/images/coupang_job.jpg', '경력3년이상', '대졸이상', 1, '2024-01-01', '2024-12-31'),
(5, '배달의민족 마케팅 전문가', '우아한형제들에서 디지털 마케팅을 담당할 마케팅 전문가를 모집합니다.', '연봉 4000만원~6000만원', '주 5일 10:00~19:00', 27, 4, '/images/baemin_job.jpg', '경력2년이상', '대졸이상', 1, '2024-01-01', '2024-12-31'),
(6, 'NCSOFT 게임 개발자', '엔씨소프트에서 차세대 온라인 게임을 개발할 게임 개발자를 모집합니다.', '연봉 4500만원~7500만원', '주 5일 09:00~18:00', 12, 1, '/images/ncsoft_job.jpg', '경력1년이상', '대졸이상', 1, '2024-01-01', '2024-12-31'),
(7, '스타트업 풀스택 개발자', '빠르게 성장하는 스타트업에서 풀스택 개발자를 찾습니다. 다양한 기술 스택을 경험할 수 있습니다.', '연봉 3000만원~5000만원', '주 5일 09:00~18:00', 3, 1, '/images/startup_job.jpg', '경력무관', '학력무관', 1, '2024-01-01', '2024-12-31');

-- JOB_POST_TAG 연결 데이터
INSERT INTO job_post_tag (job_post, tag_id) VALUES
(1, 1), (1, 2), (1, 17), -- Java, Spring, MySQL
(2, 3), (2, 4), (2, 5), -- JavaScript, React, Vue.js  
(3, 9), (3, 10), (3, 11), -- Figma, Sketch, Adobe XD
(4, 7), (4, 17), (4, 33), -- Python, MySQL, Kafka
(5, 43), (5, 44), (5, 47), -- 마케팅, 디지털마케팅, Google Analytics
(6, 40), (6, 41), (6, 15), -- Unity, Unreal Engine, Kotlin
(7, 1), (7, 3), (7, 4); -- Java, JavaScript, React

-- JOB_POST_WELFARE 데이터
INSERT INTO job_post_welfare (job_post_id, benefit_text) VALUES
(1, '4대보험 완비'), (1, '연차 15일'), (1, '점심 제공'), (1, '야근수당'),
(2, '4대보험 완비'), (2, '연차 20일'), (2, '간식 무제한'), (2, '재택근무 가능'),
(3, '4대보험 완비'), (3, '연차 15일'), (3, '교육비 지원'), (3, '건강검진'),
(4, '4대보험 완비'), (4, '연차 25일'), (4, '주식매수선택권'), (4, '자율출퇴근'),
(5, '4대보험 완비'), (5, '연차 20일'), (5, '점심/저녁 제공'), (5, '야근수당'),
(6, '4대보험 완비'), (6, '연차 15일'), (6, '개발도서 지원'), (6, '컨퍼런스 참가비'),
(7, '4대보험 완비'), (7, '연차 12일'), (7, '스톡옵션'), (7, '자율출퇴근');

-- 샘플 NOTICE 데이터
INSERT INTO notice (admin_id, notice_category_id, title, content, views) VALUES
('admin', 1, '사이트 오픈 안내', 'JobFinder 사이트가 정식 오픈하였습니다. 많은 이용 부탁드립니다.', 150),
('admin', 2, '정기 서버 점검 안내', '매주 일요일 새벽 2시~4시 서버 점검이 있습니다. 이용에 참고해주세요.', 75),
('admin', 3, '모바일 앱 출시 예정', '곧 모바일 앱이 출시될 예정입니다. 더욱 편리한 서비스를 제공하겠습니다.', 200),
('admin', 4, '회원가입 이벤트', '신규 회원가입시 추가 혜택을 드립니다. 이벤트 기간은 한정되어 있으니 서둘러주세요.', 120),
('admin', 1, '개인정보처리방침 변경 안내', '개인정보처리방침이 일부 변경되었습니다. 변경사항을 확인해주세요.', 89);

-- 샘플 COMMUNITY 데이터  
INSERT INTO community (member_id, title, content, views) VALUES
(1, 'Spring Boot 학습 후기', 'Spring Boot를 처음 배우면서 느낀 점들을 공유합니다. 초보자분들에게 도움이 되었으면 좋겠어요.', 45),
(2, 'UI/UX 트렌드 2024', '2024년 UI/UX 디자인 트렌드에 대해 이야기해봐요. 올해는 어떤 디자인이 인기일까요?', 62),
(3, '취업 준비 노하우', '면접 준비할 때 도움이 되는 팁들을 공유합니다. 같이 취업 준비하는 분들께 도움이 되길 바래요.', 88),
(1, 'MySQL vs PostgreSQL', '데이터베이스 선택 기준에 대해 토론해봅시다. 각각의 장단점이 궁금합니다.', 34),
(4, '마케팅 캠페인 성공 사례', '최근 진행했던 마케팅 캠페인의 성공 사례를 공유드립니다.', 71),
(5, 'B2B 영업 노하우', '기업 대상 영업을 할 때 효과적인 전략들을 공유합니다.', 56);

-- 샘플 POST_COMMENT 데이터
INSERT INTO post_comment (community_id, member_id, content) VALUES
(1, 2, '정말 유용한 정보네요! 감사합니다.'),
(1, 3, '저도 비슷한 경험이 있어서 공감됩니다.'),
(2, 1, '최신 트렌드 정보 감사해요.'),
(3, 2, '면접 팁 정말 도움됐습니다!'),
(4, 3, 'PostgreSQL 사용해봤는데 정말 좋더라구요.'),
(5, 1, '마케팅 전략이 인상깊네요.'),
(6, 2, '영업 노하우 잘 배웠습니다.');

-- 샘플 REPLY 데이터
INSERT INTO reply (post_comment_id, member_id, comment_content) VALUES
(1, 1, '도움이 되었다니 다행입니다!'),
(2, 1, '네, 함께 공부해요!'),
(3, 2, '더 좋은 정보 찾아서 공유할게요.'),
(4, 3, '취업 성공하시길 바랍니다!'),
(5, 4, '저도 PostgreSQL 좋아해요.'),
(6, 4, '더 많은 사례 공유하겠습니다.'),
(7, 5, '영업 성공하세요!');

-- 샘플 RESUME 데이터
INSERT INTO resume (resume_title, name, birthdate, phonenumber, email, address, self_introduction, member_id, is_public) VALUES
('백엔드 개발자 김철수', '김철수', '1990-03-15', '010-1234-5678', 'user1@example.com', '서울시 강남구 테헤란로 123', 
'안녕하세요. 3년차 백엔드 개발자 김철수입니다. Java와 Spring Framework를 주로 사용하며, 깔끔한 코드 작성을 중요하게 생각합니다.', 1, 1),

('UI/UX 디자이너 박영희', '박영희', '1992-07-22', '010-2345-6789', 'user2@example.com', '서울시 서초구 반포대로 456', 
'사용자 경험을 최우선으로 생각하는 디자이너 박영희입니다. Figma와 Sketch를 능숙하게 다루며, 데이터 기반의 디자인을 추구합니다.', 2, 1),

('기획자 이민수', '이민수', '1988-11-08', '010-3456-7890', 'user3@example.com', '경기도 성남시 분당구 판교로 789', 
'5년차 서비스 기획자 이민수입니다. 사용자 니즈 분석과 데이터 기반 의사결정을 통해 성공적인 서비스를 만들어가고 있습니다.', 3, 1);

-- 샘플 CAREER 데이터
INSERT INTO career (resume_id, company_name, department_name, hire_ym, resign_ym, position, job_id, job_group_id, salary, detail) VALUES
(1, '테크스타트업', '개발팀', '2021-03-01', '2023-12-31', '주니어 개발자', 1, 1, '3000만원', 'Spring Boot 기반 API 개발 및 데이터베이스 설계 담당'),
(2, '디자인에이전시', '디자인팀', '2020-06-01', '2023-08-31', '주니어 디자이너', 17, 3, '2800만원', '모바일 앱 UI/UX 디자인 및 브랜딩 작업 수행'),
(3, 'IT컨설팅', '기획팀', '2018-09-01', NULL, '시니어 기획자', 13, 2, '4500만원', '대기업 대상 IT 컨설팅 및 서비스 기획 업무 담당');

-- 샘플 EDUCATION 데이터
INSERT INTO education (resume_id, edu_institution, edu_name, start_date, end_date, content) VALUES
(1, '패스트캠퍼스', 'Java 웹 개발 부트캠프', '2020-09-01', '2021-02-28', 'Java, Spring Framework, MySQL을 활용한 웹 애플리케이션 개발 과정 수료'),
(2, '그린컴퓨터아카데미', 'UI/UX 디자인 전문과정', '2019-10-01', '2020-05-31', 'Figma, Sketch, Adobe XD를 활용한 UI/UX 디자인 과정 수료'),
(3, '러닝스푼즈', 'PM 양성 과정', '2017-11-01', '2018-02-28', '프로덕트 매니지먼트 및 데이터 분석 기법 학습');

-- 샘플 CERTIFICATE_RESUME 데이터
INSERT INTO certificate_resume (resume_id, certificate_name, acquisition_date, issuing_authority) VALUES
(1, '정보처리기사', '2020-11-20', '한국산업인력공단'),
(1, 'OCP(Oracle Certified Professional)', '2021-06-15', 'Oracle'),
(2, '웹디자인기능사', '2019-08-10', '한국산업인력공단'),
(2, 'GTQ 그래픽기술자격', '2019-12-05', '한국생산성본부'),
(3, 'SQLD', '2018-05-20', '한국데이터산업진흥원'),
(3, 'ADsP', '2019-03-15', '한국데이터산업진흥원');

-- 샘플 RESUME_TAG 데이터
INSERT INTO resume_tag (resume_id, tag_id) VALUES
(1, 1), (1, 2), (1, 17), (1, 27), -- Java, Spring, MySQL, Git
(2, 9), (2, 10), (2, 11), (2, 59), -- Figma, Sketch, Adobe XD, 웹디자인
(3, 42), (3, 45), (3, 46), (3, 48); -- 기획, 데이터분석, SQL, Excel

SELECT 'MySQL 샘플 데이터 삽입 완료!' as message; 