package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        return this.cloudinary
                .uploader()
                .upload(file, Collections.emptyMap())
                .get(URL)
                .toString();
    }

    @Override
    public void deleteByUrl(String url) throws IOException {
        List<String> list =  Arrays.stream(url.split("/")).collect(Collectors.toList());
        String publicId = Arrays.stream(list.get(list.size()-1).split("\\.")).collect(Collectors.toList()).get(0);
        cloudinary.uploader().destroy(publicId, Collections.emptyMap());
    }
}