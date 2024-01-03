package com.kits.ecommerce.config;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

public class Config {
    public static String hmacSHA512(final String key, final String data) {
    try {

        if (key == null || data == null) {
            throw new NullPointerException();
        }
        final Mac hmac512 = Mac.getInstance("HmacSHA512");
        byte[] hmacKeyBytes = key.getBytes();
        final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
        hmac512.init(secretKey);
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] result = hmac512.doFinal(dataBytes);
        StringBuilder sb = new StringBuilder(2 * result.length);
        for (byte b : result) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();

    } catch (Exception ex) {
        return "";
    }
}
        public static String getIpAddress(HttpServletRequest request) {
            String ipAdress;
            try {
                ipAdress = request.getHeader("X-FORWARDED-FOR");
                if (ipAdress == null) {
                    ipAdress = request.getRemoteAddr();
                }
            } catch (Exception e) {
                ipAdress = "Invalid IP:" + e.getMessage();
            }
            return ipAdress;
        }
}
