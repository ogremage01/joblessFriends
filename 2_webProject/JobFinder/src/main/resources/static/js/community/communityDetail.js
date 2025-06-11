

// 목록 페이지로 돌아가기
function goBackToList() {
	const prevUrl = sessionStorage.getItem("prevCommunityListUrl");

	if (prevUrl || prevUrl!= prevUrl) {
		location.href = prevUrl;
	} else {
		location.href = "/community";
	}
}


function deleteCommunity(communityId) {
	let url = "/community/delete/"+communityId;
	
	Swal.fire({
		title: "확인",
		text: "정말로 게시물을 삭제하시겠습니까?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "삭제",
		cancelButtonText: '취소',
		customClass: {
			confirmButton: "swalConfirmBtn",
			cancelButton: "swalCancelBtn",
		},
		reverseButtons: true, // 버튼 순서 거꾸로
	}).then((result) => {
		if (result.isConfirmed) {
			// 찜 취소 요청
			fetch(url, {
				method: "DELETE",
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(communityId)
			})
				.then(response => {
					if (!response.ok) {
						throw new Error("서버 오류: " + response.status);
					}
					return response.text();
				})
				.then(data => {
					if (data == "게시물 삭제") {
						Swal.fire({
						  title: "삭제 완료",
						  text: "게시물이 정상적으로 삭제되었습니다.",
						  icon: "success",
						  timer: 1500,
						  showConfirmButton: false
						}).then(() => {
						  location.href = '/community';
						});
					} else {
						Swal.fire({
						  icon: "error",
						  title: "삭제가 실패했습니다",
						  text: "잠시 후 다시 시도해 주세요.",
						  confirmButtonText: "확인",
				  		  customClass: {
		  					  confirmButton: "swalConfirmBtn",
		  				  },
						});
					}
				})
				.catch(error => {
					Swal.fire({
					  icon: "error",
					  title: "Oops...",
					  text: "서버와의 통신에 실패했습니다.",
					  confirmButtonText: "확인",
			  		  customClass: {
	  					  confirmButton: "swalConfirmBtn",
	  				  },
					});
				});

		}
	});
}
