package com.sxh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 一池春水倾半城
 * @date 2020年12月24日
 * @Description
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter{

  @Value("${service.upload.location}")
  private String uploadPath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry){
      //静态资源允许放行，设置缓存失效时间为24小时
      registry.addResourceHandler("/images/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/images/").setCachePeriod(3600*24);
	  //图片地址映射,映射成url就可以访问的
	  registry.addResourceHandler("/img/**").addResourceLocations("file:" + uploadPath);
  }

}
