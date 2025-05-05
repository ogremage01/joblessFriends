package com.joblessfriend.jobfinder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.joblessfriend.jobfinder.recruitment.dao")
@SpringBootApplication
public class JobFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobFinderApplication.class, args);
        System.out.println(" 스프링 부트 실행성공 ");
    }

}
