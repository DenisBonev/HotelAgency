package bg.softuni.hotelagency.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    String uploadImage(MultipartFile image) throws IOException;

    void deleteByUrl(String url) throws IOException;
}
