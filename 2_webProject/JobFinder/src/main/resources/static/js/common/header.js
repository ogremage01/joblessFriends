
var compServiceToggle = false;

$('.corpNav').on('click', function(e){
	
	e.stopPropagation();
	
	compServiceToggle = !compServiceToggle;
	
	if(compServiceToggle){
		$(".companyServiceNav").css("display","block");
	}else{
		$(".companyServiceNav").css("display","none");
	}
	
});

//외부 클릭 시 닫기
$(document).on('click', function () {
    $('.companyServiceNav').css('display', 'none');
});

let currentKeyword = '';

$(function() {
  $('#searchForm').on('submit', function(e) {
    // 입력값 앞뒤 공백 제거 후 체크
    var keyword = $('#searchKeywordInput').val().trim();
    if (keyword === '') {
      // 폼 제출(백엔드 요청) 막기
      e.preventDefault();
      // 프론트 변화 없음 (경고창 등도 띄우지 않음)
      return false;
    }
    // 입력값이 있으면 폼 제출 허용 (기존 동작)
  });
});

// 초기 URL 파라미터로부터 페이지/검색어 추출
$(document).ready(function () {
    const params = new URLSearchParams(window.location.search);
    const page = parseInt(params.get('page')) || 1;
    currentKeyword = params.get('keyword') || '';
    $('#searchKeywordInput').val(currentKeyword); // input에 값 반영
    loadPage(page, currentKeyword);
});

$('#searchForm').on('submit', function(e) {
  const keyword = $('#keyword').val().trim();
  if (!keyword) {
    e.preventDefault();
	
    $('#keyword').focus();
  }
});


// 검색 버튼 클릭 시
$(document).on('click', '#searchBtn', function () {

    currentKeyword = $('#searchKeywordInput').val().trim();
    updateUrl(1, currentKeyword); // 검색 시 1페이지로
    loadPage(1, currentKeyword);
});

// 페이지네이션 버튼 클릭 시
$(document).on('click', '.page-btn', function () {
    if ($(this).is(':disabled')) return;

    const page = $(this).data('page');
    updateUrl(page, currentKeyword); // 페이지 이동 시 URL 갱신
    loadPage(page, currentKeyword);
});

// URL을 갱신하는 함수 (주소만 바꿈, 새로고침 X)
function updateUrl(page, keyword) {
    const query = new URLSearchParams({ page, keyword }).toString();
    window.history.pushState({}, '', `/search?${query}`);
}


function loadPage(page, keyword) {
    $.ajax({
        url: '/search/json',
        type: 'GET',
        data: { page, keyword },
      success: function(response) {
          renderJobList(response.recruitmentList, response.skillMap);
          renderPagination(response.pagination);
          $('#searchSection span').html(
              `<b>'${keyword}'</b>에 대한 검색결과가 <b>총 ${response.totalCount}건</b> 있습니다.`
          );

        },
        error: function() {
            Swal.fire('데이터를 불러오지 못했습니다.');
        }
    });
}

