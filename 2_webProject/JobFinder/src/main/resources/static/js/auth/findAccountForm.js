
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
					<button type="button" id="findPwdBtn" class="btnStyle companyBtn">비밀번호 찾기</button>
				</div>	
			</fieldset>
		</form>
	`;

	$('#findFormWrap').html(formHtmlStr);

}); // navCompany end

// 사업자번호 숫자만 입력, 하이픈 자동 삽입
$(document).on('input', '#brn', function() {
	let value = $(this).val().replace(/\D/g, ''); // 숫자만 추출

	if (value.length > 3 && value.length <= 5) {
		value = value.slice(0, 3) + '-' + value.slice(3);
	} else if (value.length > 5) {
		value = value.slice(0, 3) + '-' + value.slice(3, 5) + '-' + value.slice(5, 10);
	}

	$(this).val(value);
});

//기업회원 입력정보 공백 체크
function companyBlankCheck(){
	if ($("#representative").val() == "") {
		toastPopup("담당자 명을 입력하세요.");
		return false;
	}
	if ($("#brn").val() == "") {
		toastPopup("사업자 등록번호를 입력하세요.");
		return false;
	}
	return true;
}

// 기업회원 아이디 찾기
function findCompanyId() {
	console.log("아이디 찾기");
	
	if(!companyBlankCheck()) return;
	
	//비동기 처리 필요
	var findIdForm = $("#findForm").serialize();
	
	$.ajax({
		type: 'post',
		url: '/auth/find/companyId',
		data: findIdForm,
		dataType: 'text',
		success: function(data) {
			console.log("아이디 찾기 성공");
			if(data =="없음"){
				toastPopup("아이디 찾기에 실패했습니다. 입력된 정보를 다시 확인해주십시오.");
			}else{
				idPopup(data);
			}
		},
		error: function(xhr, status, error) {
			toastPopup("오류가 발생했습니다. 잠시 후 다시 시도해주십시오.");
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

//개인회원 입력정보 공백 체크
function memberBlankCheck(){
	if ($("#email").val() == "") {
		toastPopup("이메일을 입력하세요.");
		return false;
	}else{
		return true;
	}
}

// 개인회원 이메일 존재 확인
function memberEmailExist(email){
	console.log("개인회원 이메일 존재 확인");
	return new Promise((resolve, reject) => {
		$.ajax({
			url: '/auth/check/member/email',
			method: 'POST',
			data: {email:email},
			success: function(result) {
				if (result === '존재') {
					resolve(true);
				} else {
					toastPopup("가입되지 않은 이메일입니다.");
					resolve(false);
				}
			},
			error: function() {
				alert("서버 오류가 발생했습니다.");
				reject(false); // 오류 시 false를 reject
			},
		}); // ajax end
	});	
}

// 개인회원 비번찾기 폼 submit
$("#findForm").on('submit', async function(e) {
	e.preventDefault(); // 기본 submit 막기
	
	// 빈 값 체크
	if(!memberBlankCheck()) return;
	
	// 이메일 존재 체크 후 submit 여부 결정
	var email = $("#email").val();
	var submitCheck = await memberEmailExist(email);
	
	if (submitCheck) {
        this.submit();
    } 
});

// 기업회원 이메일 확인
function companyEmailExist() {
	console.log("기업회원 이메일 확인");
	
	//비동기 처리 필요
	var findIdForm = $("#findForm").serialize();
	return new Promise((resolve, reject) => {
		$.ajax({
			type: 'post',
			url: '/auth/find/companyId',
			data: findIdForm,
			dataType: 'text',
			success: function(data) {
				if(data =='없음'){
					toastPopup("계정을 찾을 수 없습니다. 입력된 정보를 다시 확인해주십시오.");	
					resolve(false);	
				}else{
					console.log("success data: "+ data);
					resolve(data);
				}
			},
			error: function(xhr, status, error) {
				toastPopup("오류가 발생했습니다. 잠시 후 다시 시도해주십시오.");
				reject(xhr, status, error);
			}
		}); // ajax end
	});
}// companyEmailExist end

// 기업회원 비밀번호 찾기
$(document).on('click', '#findPwdBtn.companyBtn', async function() {
	console.log("기업회원 비밀번호 찾기");
	
	if(!companyBlankCheck()) return;
	
	var email = await companyEmailExist();
	console.log("email: "+email);
	
	if(!email){
		return;
	}
	
	const $form = $('#findForm');

    // 숨겨진 input 추가
    $('<input>').attr({
        type: 'hidden',
        name: 'email',
        value: email
    }).appendTo($form);

    // 동기적으로 submit (페이지 전환됨)
    $form.submit();
});
