package com.xhn.chat.chatwaveserver.base.filter;

import com.xhn.chat.chatwaveserver.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@WebFilter
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取访问令牌和刷新令牌
        String token = getTokenFromRequest(request);
        String refreshToken = getRefreshTokenFromRequest(request); // 从请求中获取刷新令牌

        if (token != null) {
            // 检查访问令牌是否有效
            if (jwtTokenUtil.validateToken(token, jwtTokenUtil.extractUsername(token))) {
                // 创建 JwtAuthenticationToken，表示当前用户的认证信息
                JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwtTokenUtil.extractUsername(token));

                // 将认证信息存入 SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if (refreshToken != null && jwtTokenUtil.validateToken(refreshToken, jwtTokenUtil.extractUsername(refreshToken))) {
                // 访问令牌无效，但刷新令牌有效
                String username = jwtTokenUtil.extractUsername(refreshToken);
                String newAccessToken = jwtTokenUtil.generateAccessToken(username); // 调用方法生成新的访问令牌
                if (newAccessToken != null) {
                    // 将新的访问令牌返回到响应中
                    response.setHeader("New-Access-Token", newAccessToken);

                    // 设置认证信息到 SecurityContext
                    JwtAuthenticationToken authentication = new JwtAuthenticationToken(username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // 继续过滤链，传递给下一个过滤器
        filterChain.doFilter(request, response);
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