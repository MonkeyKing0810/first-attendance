package com.example.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket applicationAPI(){
        return new Docket(DocumentationType.OAS_30)
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.back.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(myApi());
    }

    private ApiInfo myApi(){
        return new ApiInfoBuilder().title("考勤管理项目服务接口")
                .contact(new Contact("my","http://www.baidu.com","123qq.com"))
                .version("1.1")
                .description("接口调用及测试文档")
                .build();
    }

}
