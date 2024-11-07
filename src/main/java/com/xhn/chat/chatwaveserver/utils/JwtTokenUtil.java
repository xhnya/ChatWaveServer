package com.xhn.chat.chatwaveserver.utils;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
@Component
public class JwtTokenUtil {


    private final String secretKey = "your_secret_key";  // 替换为你的密钥

    // 验证JWT
    public boolean validateToken(String token, String username) {
        try {
            // 解析JWT，验证签名和结构
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            // 验证用户名和过期时间
            String tokenUsername = claims.getSubject();
            return (username.equals(tokenUsername) && !isTokenExpired(claims));

        } catch (SignatureException e) {
            System.out.println("无效的JWT签名：" + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("无效的JWT结构：" + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT已过期：" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("不支持的JWT：" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT为空：" + e.getMessage());
        }
        return false;
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    // 提取用户名
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
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
