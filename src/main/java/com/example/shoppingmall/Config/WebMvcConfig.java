package com.example.shoppingmall.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //자원과 외부폴더를 연결 설정
    //사용자 클래스를 Bean에 등록
    //WebMvcConfiguerer 인터페이스를 상속받아서 내용을 채운다.
    //Config 파일은 기존에 있는 스프링부트 프로그램을 수정해서 사용하는 것

    //application에 사용자 변수를 읽어온다.
    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);  //자원위치 = 물리적위치 설정
    }
}
