package com.xhn.chat.chatwaveserver.base.filter;

import com.xhn.chat.chatwaveserver.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationWebFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationWebFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = getJwtFromRequest(exchange);

        if (token != null) {
            try {
                if (jwtUtil.validateJwtToken(token)) {
                    String username = jwtUtil.getUsernameFromJwtToken(token);
                    List<SimpleGrantedAuthority> authorities = jwtUtil.getAuthorities(token)
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);

                    SecurityContextImpl securityContext = new SecurityContextImpl(authentication);

                    return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
                } else {
                    return sendErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "无效的Token");
                }
            } catch (ExpiredJwtException e) {
                return sendErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Token已过期");
            } catch (JwtException | IllegalArgumentException e) {
                return sendErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "无效的Token");
            } catch (Exception e) {
                return sendErrorResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR, "认证失败");
            }
        }

        return chain.filter(exchange); // 没有Token，放行
    }

    private String getJwtFromRequest(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private Mono<Void> sendErrorResponse(ServerWebExchange exchange, HttpStatus status, String message) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes = String.format("{\"error\": \"%s\"}", message).getBytes(StandardCharsets.UTF_8);
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }
}
