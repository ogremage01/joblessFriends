
// 기업회원 폼 전환
$("#navCompany").click(function(event) {
	console.log("기업회원 전환");
	event.preventDefault();
	$("#navUser").css(
		{
			"color": "#D4D4D4",
		}
	);
	$("#navCompany").css(
		{
			"color": "black",
		}
	);

	let formHtmlStr = `
		<form id="findForm" name="findForm" method="post" action="/auth/find/companyPwd">
			<fieldset>
				<legend>기업회원 계정찾기</legend>
				<input id="representative" name="representative" type="text" value="" placeholder="담당자 명">
				<input id="brn" name="brn" type="text" value="" placeholder="사업자 등록번호">
				
				<div style="display: flex; gap: 10px;">
					<button type="button" id="findIdBtn" class="btnStyle companyBtn" onclick="findCompanyId();">아이디 찾기</button>
					<button type="submit" id="findPwdBtn" class="btnStyle companyBtn">비밀번호 찾기</button>
				</div>	
			</fieldset>
		</form>
	`;

	$('#findFormWrap').html(formHtmlStr);

}); // navCompany end


// 아이디 찾기
function findCompanyId() {

	//비동기 처리 필요
	var findIdForm = $("#findForm").serialize();

	$.ajax({
		type: 'post',
		url: '/auth/find/companyId',
		data: findIdForm,
		dataType: 'text',
		success: function(data) {
			console.log("아이디 찾기 성공");

			alert("회원님의 아이디는 " + data + " 입니다.");
		},
		error: function(xhr, status, error) {
			alert("아이디 찾기에 실패했습니다. 입력된 정보를 다시 확인해주십시오.");
		}
	}); // ajax end

} // findCompanyId end