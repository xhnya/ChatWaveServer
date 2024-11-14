package com.xhn.chat.chatwaveserver.base.filter;

import cn.hutool.core.util.StrUtil;
import com.xhn.chat.chatwaveserver.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

@Component
@WebFilter
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenUtil jwtTokenUtil;


    private final ReactiveStringRedisTemplate redisTemplate;

    private final  Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    @Autowired
    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, ReactiveStringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取访问令牌和刷新令牌
        String token = getTokenFromRequest(request);
        String refreshToken = getRefreshTokenFromRequest(request); // 从请求中获取刷新令牌

        if (StrUtil.isBlankIfStr(token)) {
            // 如果访问令牌为空，直接放行
            filterChain.doFilter(request, response);
            return;
        }

        // 校验访问令牌有效性
        if (jwtTokenUtil.validateToken(token)) {
            // 如果访问令牌有效，创建 JwtAuthenticationToken，表示当前用户的认证信息
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwtTokenUtil.extractUsername(token));



            SecurityContextHolder.getContext().setAuthentication(authentication);  // 将认证信息存入 SecurityContext
            filterChain.doFilter(request, response);  // 继续过滤链
        } else {
            // 访问令牌无效，检查刷新令牌
            if (refreshToken != null && jwtTokenUtil.validateToken(refreshToken)) {
                // 从 Redis 中获取对应的 refreshToken
                String username = jwtTokenUtil.extractUsername(refreshToken);
                String storedRefreshToken = redisTemplate.opsForValue().get(username).block();  // 阻塞获取 Redis 中的刷新令牌

                if (storedRefreshToken != null && storedRefreshToken.equals(refreshToken)) {
                    // 如果刷新令牌有效，返回 401 未授权，并告知前端令牌已过期
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401 Unauthorized
                    response.setHeader("Refresh-Token-Valid", "true");  // 刷新令牌有效
                    writeResponse(response, "令牌过期，请刷新令牌");
                    return; // 终止请求链，避免继续传递
                } else {
                    // 如果刷新令牌无效，返回 401 错误，要求重新登录
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    writeResponse(response, "Token过期，请重新登录");
                    return; // 终止请求链，避免继续传递
                }
            } else {
                // 访问令牌和刷新令牌都无效，直接返回 401 错误，要求重新登录
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                writeResponse(response, "Token过期，请重新登录");
                return; // 终止请求链，避免继续传递
            }
        }
    }

    private void writeResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"message\": \"" + message + "\"}");
    }






    // 获取刷新令牌的方法
    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        // 可以根据实际情况实现，如从请求头或Cookie中获取
        String bearerToken = request.getHeader("X-Refresh-Token");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 从请求头中获取 token
    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // 去掉 "Bearer " 前缀
        }
        return null;
    }
}