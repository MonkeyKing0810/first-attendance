package com.example.back.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决跨域问题CORS   接受前段参数传参
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer
{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//所有接口都支持跨域
                .allowedOriginPatterns("*")//所有地址都可访问
                .allowCredentials(true)
                .allowedMethods("*")//"GET","HEAD","POST","PUT","DELETE","OPTIONS"
                .maxAge(3600);//允许跨域时间

    }


}