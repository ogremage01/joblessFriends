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

// 버튼 클릭 이벤트 바인딩
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
            Swal.fire("에러", "질문/답변을 불러오는 데 실패했습니다.", "error");
        }
    });
});
