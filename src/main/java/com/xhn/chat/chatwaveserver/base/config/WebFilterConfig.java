package com.xhn.chat.chatwaveserver.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebFilterConfig  implements WebMvcConfigurer {
    @Bean
    public WebFilter cspFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            exchange.getResponse().getHeaders().add("Content-Security-Policy",
                    "default-src 'self'; connect-src 'self' https://127.0.0.1:9000;");
            return chain.filter(exchange);
        };
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")  // 为所有路径配置 CORS
//                .allowedOrigins("http://localhost:5173") // 允许的来源
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的 HTTP 方法
//                .allowedHeaders("*")
//                .allowCredentials(true); // 允许的请求头
//
//    }
}
