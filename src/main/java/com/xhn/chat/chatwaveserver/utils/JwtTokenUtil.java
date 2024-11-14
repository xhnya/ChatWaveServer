package com.xhn.chat.chatwaveserver.utils;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
@Component
public class JwtTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);




    private final String secretKey = "f7A3@v9T!pZ0LwqK1rQeV5JzXk2bD4h";  // 替换为你的密钥

    // 验证JWT
    public boolean validateToken(String token) {
        try {
            // 解析JWT，验证签名和结构
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            // 验证用户名和过期时间
            String tokenUsername = claims.getSubject();
            return  !isTokenExpired(claims);

        } catch (SignatureException e) {
            logger.error("无效的JWT签名: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("无效的JWT结构: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT已过期: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("不支持的JWT: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT为空: {}", e.getMessage());
        }
        return false;
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }


    public String extractUsername(String token) {
        try {
            // 解析JWT，验证签名和结构
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            // 返回用户名
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            logger.error("JWT已过期: {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("无效的JWT签名: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("无效的JWT结构: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("不支持的JWT: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT为空: {}", e.getMessage());
        } catch (JwtException e) {
            logger.error("JWT 解析错误: {}", e.getMessage());
        }
        // 如果解析失败，返回 null 或抛出异常
        return null; // 或者可以选择抛出自定义的 InvalidTokenException
    }

    // 提取JWT声明（可用于额外验证）
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15分钟有效期
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7天有效期
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }



}
