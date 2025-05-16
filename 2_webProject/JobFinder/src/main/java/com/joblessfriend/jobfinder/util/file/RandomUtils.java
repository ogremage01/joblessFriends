package com.joblessfriend.jobfinder.util.file;

import java.util.UUID;

//중복되지 않는 랜덤 문자열을 생성
public class RandomUtils {
	
	//하이픈을 모두 제거하여 반환
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}
}
