package com.trade.triuserservice.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKeyStr; // 从配置文件读取

    private Key key; // 动态生成的 Key

    // 在 Bean 初始化完成后执行
    @PostConstruct
    public void init() {
        byte[] decodedSecret = Base64.getDecoder().decode(secretKeyStr);
        key = Keys.hmacShaKeyFor(decodedSecret);
    }

    // 解析Token
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 生成Token
    public String generateToken(String userId,String userName, long expireMillis) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("userName", userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
