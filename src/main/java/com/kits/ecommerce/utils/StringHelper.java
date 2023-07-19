package com.kits.ecommerce.utils;

public class StringHelper  {
    public static String extractPublicId(String imageUrl) {
        int lastSlashIndex = imageUrl.lastIndexOf("/");
        int lastDotIndex = imageUrl.lastIndexOf(".");
        if (lastSlashIndex > -1 && lastDotIndex > -1 && lastDotIndex > lastSlashIndex + 1) {
            return imageUrl.substring(lastSlashIndex + 1, lastDotIndex);
        } else {
            return "";
        }
    }
}
