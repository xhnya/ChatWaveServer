package com.xhn.chat.chatwaveserver.utils;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);




    private final String SECRET_KEY = "f7A3@v9T!pZ0LwqK1rQeV5JzXk2bD4h";  // 替换为你的密钥
    private final long ACCESS_TOKEN_EXPIRATION = 5 * 60 * 60 * 1000;  // 5 小时
    private final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000;  // 7 天

    // 生成 Access Token
    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // 生成 Refresh Token
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // 从 Token 中解析用户名
    public String getUsernameFromJwtToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // 获取 Token 中的声明（Claims）
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // 验证 Token 是否有效
    public boolean validateJwtToken(String token) {
        try {
            return !getClaimsFromToken(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        Claims claims = getClaimsFromToken(token);
        // 假设多个角色以逗号分隔存储在 "roles" 字段
        String roles = claims.get("roles", String.class);
        return Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


}
