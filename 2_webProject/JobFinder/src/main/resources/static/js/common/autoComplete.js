/*
//검색창 자동완성 - 채용공고 관련

const dataList = []; //자동완성에 뜨는 글자 배열

const searchInput = document.getElementById("searchForm");

const autoCompleteDiv = document.querySelector(".autocomplete");


searchInput.addEventListener("input", function() {
    
	const value = this.value.trim();
    autoCompleteDiv.innerHTML = ""; // 기존 추천어 초기화

    if (value.length === 0) {
			return;
		} // 입력 없으면 추천어 숨김

    const filtered = dataList.filter(item => item.includes(value));
    
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

*/