package com.puppyhome.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author cf
 * @Description: 静态资源配置 拦截器添加
 */
@Configuration
public class WebMvcConfigurerAdapter extends WebMvcConfigurationSupport {
	/**
	 * 配置静态资源
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
		/*放行swagger-ui与bootstrap-ui静态页面*/
		registry.addResourceHandler("/swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/doc.html")
				.addResourceLocations("classpath:/META-INF/resources/doc.html");
		super.addResourceHandlers(registry);
	}
}
