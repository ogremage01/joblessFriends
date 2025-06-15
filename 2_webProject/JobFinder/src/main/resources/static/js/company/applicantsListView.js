function showApplicantQnA(questionAnswerList, memberName) {
    if (!questionAnswerList || questionAnswerList.length === 0) {
        Swal.fire({
            title: `${memberName}님의 사전질문`,
            html: `<p style="text-align: center; font-size: 16px;">답변한 사전질문이 없습니다.</p>`,
            icon: "info",
            confirmButtonText: "닫기",
            customClass: {
                confirmButton: 'swalConfirmBtn'
            }
        });
        return;
    }

    let html = `
        <div style="text-align: left;">
            <p style="margin-bottom: 18px; font-size: 15px;">
                <strong>${memberName}</strong> 님의 사전질문 및 답변입니다.
            </p>
    `;

    questionAnswerList.forEach(item => {
        html += `
          <div style="margin-bottom: 24px;">
              <div style="font-weight: bold; font-size: 15px; margin-bottom: 5px;">
                  Q${item.questionOrder}. ${item.questionText}
           <div style="
                background-color: #f9f9f9;
                border: 1px solid #ddd;
                padding: 8px 14px;          
                padding-top: 6px;          
                border-radius: 6px;
                font-size: 14px;
                white-space: pre-wrap;
                line-height: 1.6;
                box-shadow: 0 1px 2px rgba(0,0,0,0.05);
                text-align: left;
                display: block;
                text-indent: 0;        /* ✅ 첫 줄 들여쓰기 제거 */
                min-height: 60px;
            ">
         ${item.answerText ? item.answerText.trim() : '<span style="color: #999;">답변 없음</span>'}

</div>

          </div>
        `;
    });

    html += `</div>`;

    Swal.fire({
        title: '📋 사전질문 답변 보기',
        html: html,
        width: 750,
        confirmButtonText: '닫기',
        customClass: {
            confirmButton: 'swalConfirmBtn'
        }
    });
}
// 질문/답변 버튼 클릭 이벤트
$(".btn-question").on("click", function () {
    const memberId = $(this).data("member-id");
    const jobPostId = $(this).data("jobpost-id");
    const memberName = $(this).closest("tr").find("td:first").text();

    $.ajax({
        url: "/company/apply/question-answer",
        method: "GET",
        data: { jobPostId, memberId },
        success: function (data) {
            showApplicantQnA(data, memberName);
        },
        error: function () {
            Swal.fire({
				title:"에러", 
				html: "질문/답변을 불러오는 데 실패했습니다.", 
				icon: "error",
				confirmButtonText: '확인',
				customClass: {
	                confirmButton: 'swalConfirmBtn'
	            }
			});
        }
    });
});


// 지원 상태 변경 모달 열기
function openStateChangeModal(jobPostId, memberId) {
    Swal.fire({
        title: '지원 상태 변경',
        html: `
          <div style="display: flex; flex-direction: column; gap: 10px;">
            <button class="applyStatus-apply swal2-confirm swal2-styled" onclick="changeState(1, ${jobPostId}, ${memberId})">지원</button>
            <button class="applyStatus-documentPass swal2-confirm swal2-styled" onclick="changeState(2, ${jobPostId}, ${memberId})">서류합격</button>
            <button class="applyStatus-finalPass swal2-confirm swal2-styled" onclick="changeState(3, ${jobPostId}, ${memberId})">최종합격</button>
            <button class="applyStatus-fail swal2-cancel swal2-styled" onclick="changeState(0, ${jobPostId}, ${memberId})">불합격</button>
          </div>
        `,
        showConfirmButton: false
    });
}


// 상태 변경 로직 (ajax)
function changeState(stateId, jobPostId, memberId) {
    $.ajax({
        url: "/company/apply/updateState",
        method: "POST",
        data: {
            jobPostId: jobPostId,
            memberId: memberId,
            stateId: stateId
        },
        success: function () {
            Swal.fire({
				title: "변경 완료", 
				html: "이력서의 지원 상태가 변경되었습니다.", 
				icon: "success",
				confirmButtonText: '확인',
		        customClass: {
		            confirmButton: 'swalConfirmBtn'
		        }
			})
                .then(() => location.reload());
        },
        error: function () {
            Swal.fire({
				title: "오류", 
				html: "상태 변경에 실패했습니다.", 
				icon: "error",
				confirmButtonText: '확인',
		        customClass: {
		            confirmButton: 'swalConfirmBtn'
		        }
			});
        }
    });
}


// 개별 버튼으로 상태 변경 (선택적 구현)
$(".btn-change-state").on("click", function () {
    const jobPostId = $(this).data("jobpost-id");
    const memberId = $(this).data("member-id");
    const newState = $(this).data("state-id");

    $.ajax({
        url: "/company/apply/updateState",
        method: "POST",
        data: {
            jobPostId: jobPostId,
            memberId: memberId,
            stateId: newState
        },
        success: function () {
            Swal.fire({
				title: "성공",
				html: "지원 상태가 변경되었습니다.",
				icon: "success",
				confirmButtonText: '확인',
		        customClass: {
		            confirmButton: 'swalConfirmBtn'
		        }
			}).then(() => location.reload());
        },
        error: function () {
            Swal.fire({
				title: "실패", 
				html: "변경 중 오류가 발생했습니다.",
				icon: "error",
				confirmButtonText: '확인',
		        customClass: {
		            confirmButton: 'swalConfirmBtn'
		        }
			});
        }
    });
});

$(".btn-resume").on("click", function () {
	const resumeId = $(this).data("resume-id");
	console.log("이력서 ID:", resumeId);
	$.post("/resume/apply/store", { resumeId: resumeId }, function () {
    window.open(
        `/resume/apply/view`,
        '_blank',
        'width=1200,height=800,top=200,left=700,scrollbars=yes,resizable=yes'
    	);
	});
});

$(document).ready(function() {
  // 1. URL에서 page 값 가져오기
  const urlParams = new URLSearchParams(window.location.search);
  const currentPage = urlParams.get('page') || '1'; // 기본값 1로 설정
  // 기존 모든 버튼에서 active 클래스 제거
   $('.page-btn').removeClass('active');
  // 2. 해당 page 값을 가진 버튼에 active 클래스 추가
  $(`.page-btn[data-page="${currentPage}"]`).addClass('active');
});

$('.page-btn[data-page="1"]').addClass('active');
function goToPage(page, jobPostId){
	location.href=`/company/apply/${jobPostId}/applicants?page=${page}`;
}
