package com.joblessfriend.jobfinder.util.service;

import java.util.Random;

import org.eclipse.angus.mail.util.logging.MailHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.auth.controller.AuthController;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final JavaMailSender javaMailSender;
	private static String tempPwd;  // 임시 비밀번호
	
	
	// 임시 비밀번호 생성
    public void creatTempPassword() {
    	int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        tempPwd = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        logger.info("임시 비밀번호 생성: {}", tempPwd);
    }
    
    // 임시 비밀번호 : 메일 형식
	public MimeMessage createMessage(String email) {
		logger.info("==createMessage==");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String mailTitle = "[어디보잡] 임시 비밀번호 안내";
        // 임시 비밀번호 생성
        creatTempPassword();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            // 메일을 받을 수신자 설정
            mimeMessageHelper.setTo(email);
            // 메일의 제목 설정
            mimeMessageHelper.setSubject(mailTitle);

            // html 문법 적용한 메일의 내용
            String content = """
			<!DOCTYPE html>
			<html>
			<head>
			  <meta charset="UTF-8">
			</head>
			<body style="font-size:14px;margin:0;padding:0;">
			  <div style="max-width:610px;margin:auto;">
			    <p>안녕하세요, <b>어디보잡</b>입니다.<br>
			    회원님의 임시 비밀번호 발급을 아래와 같이 안내드립니다.</p>
			
			    <div style="text-align:center;background-color:#f0f4f7;border-radius:8px;padding:10px;margin:15px 0;">
			      <p>임시 비밀번호<br>
			      <b>
			      """;
            content += tempPwd;
            content += """
			      </b>
			      </p>
			    </div>
			
			    <p>회원님의 개인정보 보호를 위하여 임시 비밀번호로 로그인 후에는<br>
			    반드시 비밀번호를 변경하시길 바랍니다.</p>
			    
			    <div style="text-align:center;margin-top:30px;">
			      <a href="http://localhost:9090/auth/login" style="display:inline-block;width:180px;height:50px;
			         line-height:50px;background-color:#F69800;color:white;text-decoration:none;
			         border-radius:8px;font-size:18px;font-weight:bold;">로그인 바로가기</a>
			    </div>
			  </div>
			</body>
			</html>
            """;
            
            // 메일의 내용 설정
            mimeMessageHelper.setText(content, true);

            logger.info("메일 생성 성공 content:{}", content);
            return mimeMessage;
            
        } catch (Exception e) {
        	logger.info("메일 생성 실패");
            throw new RuntimeException(e);
        }
    }
	
	// 임시 비밀번호 : 메일 발송
    public String sendTempPwdEmail(String email) {
        // 메일 전송에 필요한 정보 설정
        MimeMessage message = createMessage(email);
        // 실제 메일 전송
        javaMailSender.send(message);
        logger.info("메일 발송 성공!");
        // 인증 코드 반환
        return tempPwd;
    }
}
