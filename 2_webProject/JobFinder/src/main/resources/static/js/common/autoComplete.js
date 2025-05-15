/*
//검색창 자동완성 - 채용공고 관련

const dataList = []; //자동완성에 뜨는 글자 배열

const searchInput = document.getElementById("searchForm");

const autoCompleteDiv = document.querySelector(".autocomplete");

$.ajax({
  type: 'POST',
  url: '서버_주소',
  dataType: 'json',
  success: function (result) {
    // result는 이미 JS 배열/객체
    console.log(result);
  }
}); //자동완성에 뜨는 글자 배열


searchInput.addEventListener("input", function() {
    
	const value = this.value.trim();
    autoCompleteDiv.innerHTML = ""; // 기존 추천어 초기화

    if (value.length === 0) {
			return;
		} // 입력 없으면 추천어 숨김

    const filtered = result.filter(item => item.includes(value));
    
	filtered.forEach(item => {
        const recommendDiv = document.createElement("div");
		
        recommendDiv.textContent = item;
        recommendDiv.classList.add("autocomplete-item");
        recommendDiv.addEventListener("click", () => {
            searchInput.value = item;
            autoCompleteDiv.innerHTML = "";
        });
		
        autoCompleteDiv.appendChild(recommendDiv);
    });
});


//여기서부턴 DB

CREATE TABLE AUTOCOMPLETE_KEYWORD (
    ID         NUMBER PRIMARY KEY,
    KEYWORD    VARCHAR2(255) NOT NULL,
    FREQUENCY  NUMBER DEFAULT 1,
    UPDATED_AT DATE DEFAULT SYSDATE
);


CREATE SEQUENCE AUTOCOMPLETE_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE OR REPLACE TRIGGER AUTOCOMPLETE_KEYWORD_TRG
BEFORE INSERT ON AUTOCOMPLETE_KEYWORD
FOR EACH ROW
BEGIN
    IF :NEW.ID IS NULL THEN
        SELECT AUTOCOMPLETE_SEQ.NEXTVAL INTO :NEW.ID FROM DUAL;
    END IF;
END;
/

SELECT KEYWORD
FROM AUTOCOMPLETE_KEYWORD
WHERE KEYWORD LIKE :prefix || '%'
ORDER BY FREQUENCY DESC, UPDATED_AT DESC
FETCH FIRST 10 ROWS ONLY;

--

MERGE INTO AUTOCOMPLETE_KEYWORD a
USING (SELECT :keyword AS KEYWORD FROM DUAL) b
ON (a.KEYWORD = b.KEYWORD)
WHEN MATCHED THEN
UPDATE SET a.FREQUENCY = a.FREQUENCY + 1, a.UPDATED_AT = SYSDATE
WHEN NOT MATCHED THEN
INSERT (ID, KEYWORD, FREQUENCY, UPDATED_AT)
VALUES (AUTOCOMPLETE_SEQ.NEXTVAL, :keyword, 1, SYSDATE);

MERGE INTO AUTOCOMPLETE_KEYWORD a
→ AUTOCOMPLETE_KEYWORD 테이블을 대상으로 작업합니다.

USING (SELECT :keyword AS KEYWORD FROM DUAL) b
→ 입력받은 :keyword 값을 임시 테이블처럼 사용합니다. DUAL은 오라클에서 임시 데이터를 만들 때 사용하는 가상 테이블입니다.

ON (a.KEYWORD = b.KEYWORD)
→ AUTOCOMPLETE_KEYWORD 테이블의 KEYWORD 컬럼과 입력받은 :keyword 값이 같은지 비교합니다.

WHEN MATCHED THEN
→ 만약 같은 키워드가 이미 테이블에 있다면,

UPDATE SET a.FREQUENCY = a.FREQUENCY + 1, a.UPDATED_AT = SYSDATE
→ 해당 키워드의 빈도(FREQUENCY)를 1 증가시키고, 수정일(UPATED_AT)을 현재 시각으로 갱신합니다.

WHEN NOT MATCHED THEN
→ 만약 같은 키워드가 없다면,

INSERT (ID, KEYWORD, FREQUENCY, UPDATED_AT) VALUES (AUTOCOMPLETE_SEQ.NEXTVAL, :keyword, 1, SYSDATE);
→ 새로운 행을 추가합니다.

ID는 시퀀스(AUTOCOMPLETE_SEQ.NEXTVAL)로 자동 증가

KEYWORD는 입력받은 값

FREQUENCY는 1로 시작

UPDATED_AT은 현재 시각







*/