package com.kits.ecommerce.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void clearTokenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) { // Thay "token" bằng tên của cookie chứa token
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);

                    break;
                }
            }
        }
    }
}
