package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.Picture;
import bg.softuni.hotelagency.repository.PictureRepository;
import bg.softuni.hotelagency.service.CloudinaryService;
import bg.softuni.hotelagency.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public PictureServiceImpl(CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
        this.cloudinaryService = cloudinaryService;

        this.pictureRepository = pictureRepository;
    }

    @Override
    public void uploadHotelImages(List<MultipartFile> pictures, Hotel hotel) {

        List<Picture> pictureEntities=new ArrayList<>();
        pictures.forEach(p -> {
            try {
                String url = cloudinaryService.uploadImage(p);
                pictureRepository.save(new Picture().setUrl(url).setHotel(hotel));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
