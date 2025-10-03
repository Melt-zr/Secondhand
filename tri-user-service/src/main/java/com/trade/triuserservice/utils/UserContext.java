package com.trade.triuserservice.utils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserContext {

    private static JwtUtil jwtUtil;

    @Autowired
    public void setJwtUtil(JwtUtil injectedJwtUtil) {
        UserContext.jwtUtil = injectedJwtUtil;
    }

    public static String getCurrentUserId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getHeader("X-User-Id");
        }
        return null;
    }

    public static String getCurrentUserName() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String headerUserName = request.getHeader("X-User-Name");
            if (headerUserName != null && !headerUserName.isEmpty() && !headerUserName.contains("?")) {
                return headerUserName;
            }

            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ") && jwtUtil != null) {
                try {
                    String token = authHeader.substring(7);
                    Claims claims = jwtUtil.parseToken(token);
                    String userName = claims.get("userName", String.class);
                    return userName;
                } catch (Exception ignored) {
                }
            }
        }
        return null;
    }

    /* 判断当前用户是否已登录 */
    public static boolean isAuthenticated() {
        return getCurrentUserId() != null;
    }
}
