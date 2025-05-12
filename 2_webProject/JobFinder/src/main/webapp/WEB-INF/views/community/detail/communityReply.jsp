<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<head>

<link rel="stylesheet" href="/css/community/communityCommonStyle.css"> 
<style type="text/css">
#replyWrap {
	margin-top: 50px;
}

#inputReplyBox {
	width: 830px;
	height: 80px;
	vertical-align: top;
	padding: 10px;
	margin-bottom: 10px;
	
	resize: none;
	
	font-family: noto-sans-kt;/* 구글 기본폰트 */
}

#inputReplyWrap{

	padding-bottom: 13px;
	border-bottom: 1px solid #D1D5E3;

}

.inputBtn{
	width: 60px;
	height: 30px;
}

#replyBtnWrap{
	width: 855px;
	margin-bottom: 10px;
	margin-top: 3px;
	display: flex;
	gap: 20px;
}

#replyBtnWrap p{
	margin: 2px;
	margin-left: auto;
	
	
	color: #6D707B;
	font-size: 13px;
}
</style>
</head>

<form name="" method="" action="">
	<div id='replyWrap' class='boxStyle contentBox'>
		<div id='inputReplyWrap'>
			<p>댓글 (댓글 수-대댓미포)</p>
			<textarea id='inputReplyBox' class='boxStyle' placeholder='댓글을 입력해주세요.'></textarea>
			<div id="replyBtnWrap">
				<p>0/1000자(글자세는 부분)</p>
				<button class='inputBtn'>등록</button>
	
			</div>
		</div>
	</div>
</form>