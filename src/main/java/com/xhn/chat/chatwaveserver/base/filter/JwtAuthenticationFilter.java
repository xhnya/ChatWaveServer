package com.xhn.chat.chatwaveserver.base.filter;

import com.xhn.chat.chatwaveserver.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    private final JwtUtil jwtUtils;

    public JwtAuthenticationFilter(JwtUtil jwtUtils) {
        this.jwtUtils = jwtUtils;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtFromRequest(request);

        try {
            if (token != null) {
                // 验证Token有效性，可能抛出异常
                if (jwtUtils.validateJwtToken(token)) {
                    String username = jwtUtils.getUsernameFromJwtToken(token);
                    List<GrantedAuthority> authorities = jwtUtils.getAuthorities(token);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // Token存在但无效
                    sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "无效的Token");
                    return;
                }
            }
        } catch (ExpiredJwtException ex) {
            // Token过期
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token已过期");
            return;
        } catch (JwtException | IllegalArgumentException ex) {
            // 无效Token或解析错误
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "无效的Token");
            return;
        } catch (Exception ex) {
            // 其他未知错误
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "认证失败");
            return;
        }

        // 继续执行下一个过滤器
        filterChain.doFilter(request, response);
    }
    // 辅助方法：发送错误响应
    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(String.format("{\"error\": \"%s\"}", message));
        response.getWriter().flush();
    }
    // 从请求头中获取 JWT Token
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // 移除 "Bearer " 前缀
        }
        return null;
    }
}
