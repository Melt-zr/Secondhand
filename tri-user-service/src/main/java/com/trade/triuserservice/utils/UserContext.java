package com.trade.triuserservice.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserContext {

    public static String getCurrentUserId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getHeader("X-User-Id");
        }
        return null;
    }

    /* 判断当前用户是否已登录 */
    public static boolean isAuthenticated() {
        return getCurrentUserId() != null;
    }
}
