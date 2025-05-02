<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">

<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&icon_names=keyboard_arrow_down" />
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&icon_names=account_circle" />
<style type="text/css">

	
	#header {
		min-width: 1280px;
		width: 100%;
		border-bottom: 1px solid #E0E0E0;
	}
	
	#headerWrap {
		width: 1210px;
		height: 140px;
		margin: auto;
		margin-top: 20px;
		
	}
	
	
	/* 	헤더 위쪽 영역 */

	#headInner {
		height: 75px;
		display: flex;
		align-items:center;
	}
	
	
	/*		헤더 안에 왼쪽 여백		*/
	
	.headBlankLeft {
		margin-left: 14px;
	}
	
	
	/*		구글 아이콘 스타일		*/
	
	.material-symbols-outlined {
	  font-variation-settings:
	    'FILL' 0,
	    'wght' 400,
	    'GRAD' 0,
	    'opsz' 24;
	  vertical-align: middle;
	  font-size: 22px;
	  color: #BDBDBD;
	}

	.material-symbols-rounded {
	  font-variation-settings:
	  'FILL' 0,
	  'wght' 400,
	  'GRAD' -25,
	  'opsz' 20;
	  vertical-align: middle;
	  font-size: 22px;
	}
	
</style>

</head>

<body>

	<div id="header">
		<div id="headerWrap">
			<div id="headInner">
				<div id="logoDiv" class="headBlankLeft">
					<a href="/"><img alt="어디보잡 로고" src="/img/logo.svg" /></a>
				</div>

			</div>

		</div>
	</div>

</body>

</html>