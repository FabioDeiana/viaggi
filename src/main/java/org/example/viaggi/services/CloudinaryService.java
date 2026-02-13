package org.example.viaggi.services;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException {
        // carico il file su cloudinary e ritorno l'url
        Map result = cloudinary.uploader().upload(file.getBytes(), new HashMap<>());
        return (String) result.get("url");
    }
}