
function deleteNotice(noticeId){
	let url = "/community/notice/delete/"+noticeId;

	if(confirm('공지 사항을 삭제하시겠습니까?')){
	
		fetch(url, {
			method: 'DELETE'
		}).then(
			function(response){
				if(response.ok){

					location.href = '/community/notice';
				}
			}
		)
	}
}
