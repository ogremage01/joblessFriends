
function deleteCommunity(communityId){
	let url = "/community/delete/"+communityId;

	if(confirm('게시물을 삭제하시겠습니까?')){
	
		fetch(url, {
			method: 'DELETE'
		}).then(
			function(response){
				if(response.ok){
					alert("게시물이 성공적으로 삭제되었습니다.");
					
					location.href = '/community';
				}
			}
		)
	}
	
}
