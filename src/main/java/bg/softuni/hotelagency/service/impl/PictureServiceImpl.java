package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.Picture;
import bg.softuni.hotelagency.model.exception.EntityNotFoundException;
import bg.softuni.hotelagency.repository.PictureRepository;
import bg.softuni.hotelagency.service.CloudinaryService;
import bg.softuni.hotelagency.service.HotelService;
import bg.softuni.hotelagency.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;
    private final HotelService hotelService;

    public PictureServiceImpl(CloudinaryService cloudinaryService, PictureRepository pictureRepository, HotelService hotelService) {
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
        this.hotelService = hotelService;
    }

    @Override
    public void uploadHotelImages(List<MultipartFile> pictures, Long hotelId) {

        Hotel hotel = hotelService.getHotelById(hotelId);
        Collections.reverse(pictures);
        pictures.forEach(p -> {
            String url = null;
            try {
                url = cloudinaryService.uploadImage(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
            pictureRepository.save(new Picture().setUrl(url).setHotel(hotel));

        });
    }

    @Override
    public List<String> getPicturesByHotelId(Long id) {
        return pictureRepository.getPicturesByHotelId(id);
    }

    @Override
    public void deleteByUrl(String url) throws IOException {
        pictureRepository.delete(pictureRepository.findPictureByUrl(url).
                orElseThrow(() -> new EntityNotFoundException("Picture")));
        cloudinaryService.deleteByUrl(url);
    }

}
