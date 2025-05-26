
function deleteCommunity(communityId){
	let url = "/community/delete/"+communityId;

	if(confirm('게시물을 삭제하시겠습니까?')){
	
		fetch(url, {
			method: 'DELETE'
		}).then(
			function(response){
				if(response.ok){

					location.href = '/community';
				}
			}
		)
	}
}
