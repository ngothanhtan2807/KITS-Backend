package com.kits.ecommerce.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kits.ecommerce.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private final Cloudinary cloudinary;



    public FileServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {

        Map<String, String> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        String img = (String) result .get("secure_url");
//
//        u.setAvatar(img);

        return result.getOrDefault("secure_url", "");
    }
    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
