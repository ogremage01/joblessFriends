<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">

<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&icon_names=keyboard_arrow_down" />
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200&icon_names=account_circle" />

<style type="text/css">

	#footer {
		min-width: 1280px;
		width: 100%;
		border-top: 1px solid #e6e8f1;
		margin-top: 250px;
		background-color: #fbfbfd;
	}
	
	#footerWrap {
		width: 1210px;
		margin: auto;
		padding-top: 60px;
		padding-bottom: 100px;
		
		display: flex;
		justify-content: space-between;
	}
	
	.footerBox {
		display: flex;
		gap: 45px;
		position: relative;
	}
	
	.footerBox>div {
		display: flex;
		flex-direction: column;
	}
	
	.footer-title {
		font-size: 16px;
		font-weight: bold;
	}
	
	.gitTitle {
		margin-right: 25px;
	}
	
	.footer-icon {
		width: 25px;
		position: absolute;
		left: 70px;
		mix-blend-mode: multiply;
	}
	
	.goGitBtn {
		background-color: white;
		border: 1px solid #D1D5E3;
		border-radius: 30px;
		padding: 10px;
		user-select:none;
		cursor: pointer;
	}
	
	
</style>

</head>

<body>

	<div id="footer">
		<div id="footerWrap">
			<div class="footer-Left footerBox">
			
				<div>
					<span class="footer-title">PROJECT 어디보잡</span>
					<span class="footer-info">2025.04.25~2025.06.16</span>
				</div>
				
				<div>
					<span class="footer-title">TEAM 잡없는녀석들</span>
					<span class="footer-info">오우람 박찬정 전유빈 정현식 주찬미 최유혁</span>
				</div>
				
			</div>

			<div class="footer-right footerBox">
			
				<div class="goGitBtn" tar onclick="window.open('https://github.com/ogremage01/joblessFriends.git')">
					<span class="footer-title gitTitle">
						GitHub
					</span>
					<img class="footer-icon" alt="깃허브 로고" src="https://github.githubassets.com/assets/GitHub-Mark-ea2971cee799.png">
				</div>
				
			</div>
		</div>
	</div>

</body>

</html>