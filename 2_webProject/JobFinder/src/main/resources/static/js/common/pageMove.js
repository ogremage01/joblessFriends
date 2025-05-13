//메인
function pageMoveMainFnc() { location.href = '/'; }

//auth
	//로그인
	//회원가입
	function pageMoveAuthSignUpFnc() { location.href = '/auth/signup'; }

//커뮤니티
	//목록
	function pageMoveCommunityListFnc() { location.href = '/community'; }
	
	//업로드
	function pageMoveCommunityUploadFnc() { location.href = '/community/a'; }
	
	//상세
	function pageMoveCommunityDetailFnc() { location.href = '/community/d'; }


//채용정보
	//목록
	function pageMoveRecruitmentListFnc() { location.href = '/Recruitment/list'; }
	//검색
	function pageMoveSearchListFnc() {
		const form = document.getElementById('searchForm');
		
		const queryParams = {
		            page: (page) ? page : 1,
		            recordSize: 8,
		            pageSize: 10,
		            keyword: form.keyword.value
		        }

	        /*
	         *    location.pathname : 리스트 페이지의 URI("/Recruitment/list")를 의미
	         *    new URLSearchParams(queryParams).toString() : queryParams의 모든 프로퍼티(key-value)를 쿼리 스트링으로 변환
	         *    URI + 쿼리 스트링에 해당하는 주소로 이동
	         *    (해당 함수가 리턴해주는 값을 브라우저 콘솔(console)에 찍어보시면 쉽게 이해하실 수 있습니다.)
	         */
	        location.href = location.pathname + '?' + new URLSearchParams(queryParams).toString();
	}

//관리자
	//로그인
	function pageMoveAdminLoginFnc() { location.href = '/admin/login'; }
	
	//메인
	function pageMoveAdminMainFnc() { location.href = '/admin/main'; }
	
	//회원
	function pageMoveAdminMemberFnc() { location.href = '/admin/member'; }