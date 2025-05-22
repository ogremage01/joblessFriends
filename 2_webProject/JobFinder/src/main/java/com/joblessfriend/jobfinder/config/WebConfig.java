package com.joblessfriend.jobfinder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.joblessfriend.jobfinder.config.intercepter.AdminLoginCheckIntercepter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AdminLoginCheckIntercepter adminLoginCheckIntercepter;

    public WebConfig(AdminLoginCheckIntercepter adminLoginCheckIntercepter) {
        this.adminLoginCheckIntercepter = adminLoginCheckIntercepter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminLoginCheckIntercepter)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login", "/admin/css/**", "/admin/js/**");
    }


}



