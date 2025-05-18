
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
	console.log("아이디 찾기");
	
	if ($("#representative").val() == "") {
		toastPopup("담당자 명을 입력하세요.");
		return false;
	}
	if ($("#brn").val() == "") {
		toastPopup("사업자 등록번호를 입력하세요.");
		return false;
	}
	//비동기 처리 필요
	var findIdForm = $("#findForm").serialize();
	
	$.ajax({
		type: 'post',
		url: '/auth/find/companyId',
		data: findIdForm,
		dataType: 'text',
		success: function(data) {
			console.log("아이디 찾기 성공");

			idPopup(data);
		},
		error: function(xhr, status, error) {
			toastPopup("아이디 찾기에 실패했습니다. 입력된 정보를 다시 확인해주십시오.");
		}
	}); // ajax end

} // findCompanyId end


function toastPopup(msg) {
	$('#askConfirm').html(msg);
	$('#askConfirm').attr('class', 'active');
	setTimeout(function() {
		$('#askConfirm').removeClass('active');
	}, 1500);
}

var idCheck = false;
function idPopup(data) {
	if(idCheck) return;
	idCheck = true;
	var msg= "회원님의 아이디는 " + data + " 입니다. "
	msg += "<button id='copyBtn' onclick='idCopy(\"" + data + "\")'>복사</button>";
	
	$('#askConfirm').html(msg);
	$('#askConfirm').attr('class', 'active');
}

function idCopy(data){
	navigator.clipboard.writeText(data);
	$('#askConfirm').removeClass('active');
	setTimeout(function() {
			toastPopup("복사되었습니다.");
			idCheck=false;
	}, 400);
}
