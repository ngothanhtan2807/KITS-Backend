package com.kits.ecommerce.services.impl;


import com.cloudinary.Cloudinary;
import com.kits.ecommerce.services.FileUploadCloudinary;
import com.kits.ecommerce.utils.StringHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadCloudinaryImpl implements FileUploadCloudinary {

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }

    @Override
    public void deleteImage(String imageUrl) {
        try {
            // Trích xuất public ID từ URL

            String publicId =   StringHelper.extractPublicId(imageUrl);

            // Xóa hình ảnh từ Cloudinary
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

            System.out.println("Hình ảnh đã được xóa thành công từ Cloudinary.");

        } catch (Exception e) {
            System.out.println("Xóa hình ảnh không thành công từ Cloudinary. Lỗi: " + e.getMessage());
        }
    }


}
