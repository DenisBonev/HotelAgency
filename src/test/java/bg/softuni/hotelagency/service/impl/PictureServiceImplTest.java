package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.Picture;
import bg.softuni.hotelagency.model.exception.EntityNotFoundException;
import bg.softuni.hotelagency.repository.PictureRepository;
import bg.softuni.hotelagency.service.CloudinaryService;
import bg.softuni.hotelagency.service.HotelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PictureServiceImplTest {

    private PictureServiceImpl serviceToTest;


    @Mock
    CloudinaryService cloudinaryService;
    @Mock
    PictureRepository pictureRepository;
    @Mock
    HotelService hotelService;

    @BeforeEach
    public void setUp() {
        serviceToTest = new PictureServiceImpl(cloudinaryService, pictureRepository, hotelService);
    }

    @Test
    public void testGetPicturesByHotelId() {
        Hotel hotel1 = new Hotel();
        hotel1.setName("hotel1").setId(1L);
        Hotel hotel2 = new Hotel();
        hotel1.setName("hotel2").setId(2L);

        Picture picture1 = new Picture();
        picture1.setUrl("testUrl1.com").setHotel(hotel1);
        Picture picture2 = new Picture();
        picture2.setUrl("testUrl2.com").setHotel(hotel1);
        Picture picture3 = new Picture();
        picture3.setUrl("testUrl3.com").setHotel(hotel2);

        when(pictureRepository.getPicturesByHotelId(hotel1.getId())).
                thenReturn(List.of(picture1.getUrl(), picture2.getUrl()));

        //act
        List<String> urls = serviceToTest.getPicturesByHotelId(hotel1.getId());
        //assert
        assertEquals(2, urls.size());
        assertEquals(picture1.getUrl(), urls.get(0));
        assertEquals(picture2.getUrl(), urls.get(1));
    }

    @Test
    public void testDeleteByUrlThrows() throws IOException {
        String fakeUrl = "incorrect url";
        when(pictureRepository.findPictureByUrl(fakeUrl)).
                thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serviceToTest.deleteByUrl(fakeUrl));
    }
}
