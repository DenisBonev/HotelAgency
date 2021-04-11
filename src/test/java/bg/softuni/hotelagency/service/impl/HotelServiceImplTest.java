package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.entity.UserRole;
import bg.softuni.hotelagency.model.entity.enums.RoleEnum;
import bg.softuni.hotelagency.model.entity.enums.StarEnum;
import bg.softuni.hotelagency.model.exception.EntityNotFoundException;
import bg.softuni.hotelagency.model.service.HotelServiceModel;
import bg.softuni.hotelagency.repository.HotelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {

    HotelServiceImpl serviceToTest;

    private Hotel hotel;
    private User user;
    private final ModelMapper modelMapper = new ModelMapper();

    HotelRepository mockHotelRepository = Mockito.mock(HotelRepository.class);

    @BeforeEach
    public void setUp() {

        UserRole userRole = new UserRole();
        userRole.setName(RoleEnum.USER);

        UserRole ownerRole = new UserRole();
        ownerRole.setName(RoleEnum.HOTEL_OWNER);

        user = new User();
        user.
                setEmail("testUser@mail.com").
                setPassword("testpass").
                setFirstName("Test").
                setLastName("Petrov").
                setRoles(List.of(ownerRole, userRole));

        hotel = new Hotel();
        hotel.
                setName("Test Hotel").
                setEmail("test@mail").
                setStars(StarEnum.FIVE).
                setAddress("test 12").
                setDescription("testing...").
                setOwner(user).
                setId(12L);

        serviceToTest = new HotelServiceImpl(mockHotelRepository, new ModelMapper());
    }

    @Test
    public void testGetHotelById() {

        when(mockHotelRepository.findById(12L)).thenReturn(Optional.of(hotel));

        Hotel returnedHotel = serviceToTest.getHotelById(12L);

        assertEquals(hotel.getId(), returnedHotel.getId());
        assertEquals(hotel.getName(), returnedHotel.getName());
        assertEquals(hotel.getOwner(), returnedHotel.getOwner());
        assertEquals(hotel.getStars(), returnedHotel.getStars());
    }

    @Test
    public void testGetHotelsByHotelOwner() {
        when(mockHotelRepository.getAllByOwnerEmail(user.getEmail())).thenReturn(List.of(hotel));
        when(mockHotelRepository.getAllByOwnerEmail("fakeEmail")).thenReturn(List.of());

        List<HotelServiceModel> incorrectEmailServiceModels = serviceToTest.getHotelsByOwnerEmail("fakeEmail");
        List<HotelServiceModel> hotelServiceModels = serviceToTest.getHotelsByOwnerEmail(user.getEmail());

        assertEquals(0, incorrectEmailServiceModels.size());
        assertEquals(1, hotelServiceModels.size());
        assertEquals(hotel.getId(), hotelServiceModels.get(0).getId());
        assertEquals(hotel.getOwner().getEmail(), hotelServiceModels.get(0).getOwner().getEmail());
        assertEquals(hotel.getName(), hotelServiceModels.get(0).getName());
    }


    @Test
    public void testGetAllHotels() {
        Hotel hotel2 = new Hotel();
        hotel2.setName("Second Hotel").setId(2L);
        when(mockHotelRepository.findAll()).thenReturn(List.of(hotel, hotel2));

        List<HotelServiceModel> hotelServiceModels = serviceToTest.getAllHotels();

        assertEquals(2, hotelServiceModels.size());

        assertEquals(hotel.getId(), hotelServiceModels.get(0).getId());
        assertEquals(hotel.getName(), hotelServiceModels.get(0).getName());
        assertEquals(hotel.getEmail(), hotelServiceModels.get(0).getEmail());

        assertEquals(hotel2.getName(), hotelServiceModels.get(1).getName());
        assertEquals(hotel2.getId(), hotelServiceModels.get(1).getId());
    }

    @Test
    public void testSaveChanges() {
        HotelServiceModel hotelServiceModel = new HotelServiceModel();
        hotelServiceModel.setId(hotel.getId());
        hotelServiceModel.setName("Updated").
                setStars(StarEnum.FIVE).
                setEmail("update@email.com").
                setAddress("updated address");
        when(mockHotelRepository.findById(hotelServiceModel.getId())).
                thenReturn(Optional.of(hotel));

        serviceToTest.saveChanges(hotelServiceModel);

        assertEquals(hotelServiceModel.getId(), hotel.getId());
        assertEquals(hotelServiceModel.getName(), hotel.getName());
        assertEquals(hotelServiceModel.getStars(), hotel.getStars());
        assertEquals(hotelServiceModel.getEmail(), hotel.getEmail());
        assertEquals(hotelServiceModel.getAddress(), hotel.getAddress());
    }

    @Test
    public void testSaveChangesThrow() {
        HotelServiceModel hotelServiceModel = new HotelServiceModel();
        //Fake ID
        hotelServiceModel.setId(999L);
        hotelServiceModel.
                setName("Updated").
                setStars(StarEnum.FIVE).
                setEmail("update@email.com").
                setAddress("updated address");
        when(mockHotelRepository.findById(hotelServiceModel.getId())).
                thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class,()->serviceToTest.saveChanges(hotelServiceModel));
    }

}
