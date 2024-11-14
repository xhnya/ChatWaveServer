package com.xhn.chat.chatwaveserver.base.config;

import com.xhn.chat.chatwaveserver.base.filter.JwtAuthenticationFilter;
import com.xhn.chat.chatwaveserver.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;
    public SecurityConfig(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    private MyUnauthorizedHandler unauthorizedHandler;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Collections.emptyList());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // 禁用 CSRF 防护
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // 配置跨域
                // 配置请求授权规则，使用新的 authorizeHttpRequests
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/user/login","/user/referToken").permitAll()  // 允许不认证的请求
                        .anyRequest().authenticated()               // 其他请求需要认证
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenUtil,redisTemplate), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 使用无状态认证方式
                .formLogin(AbstractHttpConfigurer::disable); // 使用自定义的认证入口点
                // 配置异常处理，未认证时返回 401 错误


        return http.build();
    }
}