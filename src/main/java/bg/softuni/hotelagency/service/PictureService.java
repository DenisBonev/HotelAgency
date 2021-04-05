package bg.softuni.hotelagency.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PictureService {
    void uploadHotelImages(List<MultipartFile> pictures, Long hotelId);

    List<String> getPicturesByHotelId(Long id);

    void deleteByUrl(String url) throws IOException;
}
