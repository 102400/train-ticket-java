package com.stiei.trainticket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stiei.trainticket.interceptor.AuthWebRequestInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@MapperScan(value = "com.stiei.trainticket.mapper")
public class TrainticketApplication extends WebMvcConfigurerAdapter {

	@Autowired private AuthWebRequestInterceptor authWebRequestInterceptor;

//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//
//	}

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(authWebRequestInterceptor);
//	}

	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry.addMapping("/api/*").allowedOrigins("*");
	}

	public static void main(String[] args) {
		SpringApplication.run(TrainticketApplication.class, args);
	}
}