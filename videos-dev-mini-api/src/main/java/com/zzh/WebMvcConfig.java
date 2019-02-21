package com.zzh;


import com.zzh.controller.interceptor.LoginInterceptor;
import com.zzh.controller.interceptor.MiniInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
		.addResourceLocations("classpath:/META-INF/resources/")
//				.addResourceLocations("file:/developer/videos_dev/");
				.addResourceLocations("file:D:/videos_dev/");
	}

	@Bean
	public MiniInterceptor miniInterceptor() {
		return new MiniInterceptor();
	}

	@Bean
	public LoginInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(miniInterceptor()).addPathPatterns("/user/**")
				.addPathPatterns("/video/upload", "/video/uploadCover",
						"/video/userLike", "/video/userUnLike",
						"/video/saveComment", "/video/likeComment",
						"/video/unlikeComment")
				.addPathPatterns("/bgm/**")
				.excludePathPatterns("/user/queryPublisher");

		registry.addInterceptor(loginInterceptor())
				.addPathPatterns("/admin/**")
				.excludePathPatterns("/admin/login")
				.excludePathPatterns("/admin/loginCheckout")
				.excludePathPatterns("/admin/logout");
		super.addInterceptors(registry);
	}


}
