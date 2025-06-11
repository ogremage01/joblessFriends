-- Oracle 11_community_Comment_data.sql을 MySQL로 변환
-- COMMUNITY 테이블의 커뮤니티 데이터

SET FOREIGN_KEY_CHECKS = 0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;

-- 기존 COMMUNITY 데이터 삭제
DELETE FROM COMMUNITY;

-- COMMUNITY 데이터 삽입 (AUTO_INCREMENT 컬럼에는 NULL 사용, SYSDATE를 NOW()로, TO_DATE를 직접 날짜 문자열로)
INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 8, '비대면 면접 시 조명 팁', 
'비대면 면접에서는 조명이 매우 중요합니다. 
자연광을 최대한 활용하는 것이 가장 좋으며, 만약 자연광이 부족하다면, 정면에 위치한 링라이트나 LED 조명을 사용하는 것이 좋습니다. 빛이 너무 강하거나 약하지 않도록 조절하고, 배경은 단색이나 깔끔한 공간으로 준비하는 것이 면접관에게 좋은 인상을 줄 수 있습니다. 
조명 외에도 카메라 각도와 음향 환경도 체크하여 최적의 상태로 면접에 임하시길 바랍니다.', 
'2025-01-01 00:00:00', NULL, 11);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 2, '인턴 후 정규직 전환 성공기', 
'인턴십 기간 동안 좋은 평가를 받기 위해서는 맡은 업무를 정확히 이해하고, 성실하게 수행하는 것이 중요합니다. 

또한, 팀원들과 원활한 소통을 유지하며 피드백을 적극적으로 수용하는 자세가 필요합니다. 

정규직 전환을 목표로 한다면 회사의 문화와 업무 방식을 빨리 익히고, 필요한 역량을 꾸준히 개발하는 것도 도움이 됩니다. 

마지막으로, 본인의 성장 의지와 열정을 면담 시 잘 어필하는 것이 성공 확률을 높입니다.', 
'2025-01-04 00:00:00', NULL, 15);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 5, '면접복장 정장 꼭 입어야 하나요?', 
'대부분의 기업에서는 면접 시 정장을 기본 복장으로 권장하지만, 최근 IT나 스타트업 같은 산업군에서는 비즈니스 캐주얼을 허용하는 경우가 많습니다. 

따라서 지원하는 회사의 분위기를 미리 파악하는 것이 중요하며, 회사 홈페이지나 면접 안내 메일에서 복장 규정을 확인하는 것이 좋습니다. 

정장을 입을 경우에는 깨끗하고 잘 다림질된 상태인지, 신발이나 액세서리까지 신경 쓰는 것이 첫인상에 큰 영향을 줍니다.', 
'2025-01-07 00:00:00', NULL, 3);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 3, '취업 스펙 쌓기 좋은 자격증 추천', 
'취업 준비를 할 때 자격증은 실무 능력을 증명하는 좋은 수단입니다. 
초보자도 도전하기 좋은 자격증으로는 컴퓨터 활용 능력, 한자 자격증, 그리고 각 직무별 전문 자격증이 있습니다. 

자격증 취득은 단기간에 끝내기보다는 꾸준히 준비하여 실력을 쌓는 과정으로 생각하는 것이 좋으며, 관련 업계에서 어떤 자격증을 우선시하는지도 조사해보세요.', 
'2025-01-10 00:00:00', NULL, 8);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 1, '온라인 코딩 테스트 준비 방법', 
'온라인 코딩 테스트는 많은 IT 기업에서 기본 평가 과정입니다. 문제 풀이 연습은 다양한 사이트(백준, 프로그래머스 등)를 활용하고, 자료구조와 알고리즘 기본 개념을 탄탄히 해야 합니다. 또한 제한 시간 내 문제를 푸는 연습과, 코딩 스타일을 깔끔하게 유지하는 습관도 중요합니다. 모의 테스트를 꾸준히 하면서 실전 감각을 익히세요.', 
'2025-01-13 00:00:00', NULL, 9);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 7, '퇴사 후 재취업 얼마나 걸리셨나요?', 
'퇴사 후 재취업 기간은 개인마다 다르지만, 평균적으로 3~6개월 정도 걸리는 경우가 많습니다. 

준비 기간을 단축하려면 빠르게 이력서와 자기소개서를 준비하고, 네트워킹을 통해 정보를 얻는 것이 도움이 됩니다. 

또한 면접 준비와 자기계발에 시간을 투자하여 경쟁력을 높이세요.', 
'2025-01-16 00:00:00', NULL, 4);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 4, '면접 때 어필할 만한 경험', 
'면접에서는 본인의 경험을 구체적으로 이야기하는 것이 중요합니다. 단순히 "열심히 했다"는 표현보다는 어떤 문제를 어떻게 해결했는지, 그리고 그 결과가 어땠는지를 중심으로 설명하세요. 팀워크, 리더십, 문제해결능력 등 직무와 관련된 경험을 강조하는 것이 효과적입니다.', 
'2025-01-19 00:00:00', NULL, 7);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 9, '면접 중 긴장 푸는 팁', 
'면접 전 긴장을 줄이기 위해서는 심호흡, 가벼운 스트레칭, 긍정적인 자기 암시를 해보세요. 또한 면접 전에 예상 질문을 준비하고 답변을 연습하는 것도 자신감을 높여줍니다. 면접 장소에 일찍 도착하여 환경에 익숙해지는 것도 긴장 완화에 도움이 됩니다.', 
'2025-01-22 00:00:00', NULL, 9);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 6, '이직 시 포트폴리오 중요성', 
'이직을 준비할 때 포트폴리오는 본인의 역량과 경험을 효과적으로 보여주는 도구입니다. 특히 디자인, 개발, 마케팅 분야에서는 잘 구성된 포트폴리오가 채용 담당자에게 큰 인상을 줍니다. 프로젝트 내용, 본인의 역할, 결과물을 체계적으로 정리하여 보여주도록 하세요.', 
'2025-01-25 00:00:00', NULL, 11);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 10, '신입 지원서에 경력 입력해도 되나요?', 
'신입 지원서에도 아르바이트, 인턴, 동아리 활동 등 경력을 작성하는 것은 좋습니다. 관련 경험이 있다면 적극적으로 적어 지원자의 열정과 실무 경험을 어필할 수 있습니다. 다만, 허위 내용은 절대 피해야 합니다.', 
'2025-01-28 00:00:00', NULL, 19);

-- 추가 COMMUNITY 데이터들...
INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 3, '신입 취업 성공기 공유합니다', '신입으로 이번에 최종 합격했어요! 제 경험이 다른 분들께도 도움이 되었으면 합니다.', '2025-02-02 00:00:00', NULL, 0);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 7, '모르는 번호로 온 면접 연락?', '면접 장소가 카페라는데 괜찮은 걸까요? 요즘은 그런 경우도 있나요?', '2025-02-05 00:00:00', NULL, 0);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 1, '근무시간 단축 가능한 회사 있나요?', 
'최근에는 워라밸(일과 삶의 균형)을 중요시하는 기업들이 늘고 있어, 근무시간 단축이나 유연근무제를 도입하는 회사가 많습니다. 특히 IT, 스타트업, 대기업의 일부 부서에서 이러한 제도를 운영하고 있으니 구직 시 해당 부분을 확인해 보세요.', 
'2025-02-06 00:00:00', NULL, 2);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 4, '신입 면접 질문 예시 공유', 
'신입 면접에서는 자기소개, 지원 동기, 학창 시절 경험, 단점과 장점, 팀워크 경험 등에 관한 질문이 자주 나옵니다. 각 질문에 대해 미리 답변을 준비하고 구체적인 사례를 들어 설명하는 연습을 하세요.', 
'2025-02-09 00:00:00', NULL, 4);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 5, '재택근무 가능한 직종 추천', 
'재택근무가 가능한 직종으로는 IT 개발자, 디자이너, 콘텐츠 제작자, 마케팅, 고객지원 등이 있습니다. 특히 최근에는 다양한 직무에서 원격 근무가 활성화되고 있으니, 채용 공고에서 재택근무 가능 여부를 꼭 확인해 보세요.', 
'2025-02-12 00:00:00', NULL, 4);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 4, '기업 선택 기준 뭐가 중요할까요?', '여러 기업 중 어디를 선택해야 할지 고민이에요. 어떤 기준이 중요할까요?', '2025-02-12 00:00:00', NULL, 13);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 6, '잡포털 말고 다른 취업 루트?', '요즘 채용 정보는 어디서 얻으시나요? 잡포털 외에 다른 루트도 있나요?', '2025-02-17 00:00:00', NULL, 10);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 8, '비대면 면접 시 조명 팁', '비대면 면접 보실 때 조명은 어떻게 준비하시나요? 화면에 너무 어둡게 나와요.', '2025-02-22 00:00:00', NULL, 6);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 2, '인턴 후 정규직 전환 성공기', '인턴을 거쳐 정규직 전환에 성공했습니다! 준비했던 내용 공유드릴게요.', '2025-02-26 00:00:00', NULL, 8);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 5, '면접복장 정장 꼭 입어야 하나요?', '면접 시 정장 꼭 입어야 할까요? 캐주얼 가능하다고 해도 정장이 기본인가요?', '2025-03-01 00:00:00', NULL, 13);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 10, '경력직 이직 시 연봉 협상법', '경력직으로 이직하실 때 연봉 협상 어떻게 하시나요? 팁이 필요합니다.', '2025-03-04 00:00:00', NULL, 20);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 5, '경력직 지원 시 유의사항', 
'경력직 지원 시에는 이전 경력과 성과를 구체적으로 어필하는 것이 중요합니다. 이력서와 자기소개서에 프로젝트 결과, 맡은 역할, 사용한 기술 등을 명확히 작성하고, 면접에서는 경험을 토대로 문제 해결 사례를 상세히 설명하세요.', 
'2025-03-05 00:00:00', NULL, 7);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 1, '면접관 질문 의도 파악법', 
'면접관이 질문하는 의도를 정확히 파악하면 답변의 질이 높아집니다. 

질문 배경과 핵심을 이해하고, 그에 맞는 경험과 역량을 중심으로 구체적이고 진솔하게 답변하도록 연습하세요.', 
'2025-03-08 00:00:00', NULL, 9);

INSERT INTO COMMUNITY (COMMUNITY_ID, MEMBER_ID, TITLE, CONTENT, CREATE_AT, MODIFIED_AT, VIEWS)
VALUES (NULL, 6, '자격증보다 중요한 실무 경험', 
'많은 구직자들이 자격증에만 집중하지만, 실제로 기업에서는 실무 경험을 더 중시하는 경우가 많습니다. 인턴, 프로젝트, 프리랜서 경험 등을 통해 실무 역량을 키우고 이를 면접에서 효과적으로 어필하는 것이 필요합니다.', 
'2025-03-11 00:00:00', NULL, 14);

COMMIT;
SET FOREIGN_KEY_CHECKS = 1; 