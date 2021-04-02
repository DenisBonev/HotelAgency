package bg.softuni.hotelagency.service;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PictureService {
    void uploadHotelImages(List<MultipartFile> pictures, Hotel hotel);

    List<String> getPicturesByHotelId(Long id);
}
