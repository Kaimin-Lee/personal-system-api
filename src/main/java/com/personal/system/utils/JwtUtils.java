package com.personal.system.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {
    // 秘钥（请保管好，最好放在配置文件里，这里为了方便演示直接写死）
    private static final String SECRET_KEY = "MyPersonalSystemSecretKey";
    // 过期时间：7天 (毫秒)
    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7;

    /**
     * 生成 Token
     */
    public static String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // 将 userId 存入 token
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 签名算法和秘钥
                .compact();
    }

    /**
     * 解析 Token 获取 userId
     */
    public static Long getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            return null; // 解析失败或已过期
        }
    }
}