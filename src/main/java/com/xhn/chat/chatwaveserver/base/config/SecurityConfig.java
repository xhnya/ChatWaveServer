package com.xhn.chat.chatwaveserver.base.config;

import com.xhn.chat.chatwaveserver.base.filter.JwtAuthenticationFilter;
import com.xhn.chat.chatwaveserver.utils.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtTokenUtil jwtTokenUtil;

    public SecurityConfig(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/public/**").permitAll()  // 允许公共访问
                        .anyRequest().authenticated()               // 其他请求需要认证
                )
                .logout(logout -> logout                       // 配置注销
                        .logoutSuccessUrl("/")                     // 注销成功后的重定向
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenUtil), UsernamePasswordAuthenticationFilter.class); // 添加JWT过滤器;

        return http.build();
    }
}