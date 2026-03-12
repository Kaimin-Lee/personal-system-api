package com.personal.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许跨域访问的路径
        registry.addMapping("/**")
                // 允许跨域访问的源（注意：Spring Boot 2.4 以上版本使用 allowedOriginPatterns 代替 allowedOrigins）
                .allowedOriginPatterns("*")
                // 允许请求的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许的请求头
                .allowedHeaders("*")
                // 是否允许携带凭证（如 Cookie 等）
                .allowCredentials(true)
                // 预检请求的缓存时间（秒），也就是在这个时间段内，同一跨域请求不再发送 OPTIONS 请求预检
                .maxAge(3600);
    }
}
