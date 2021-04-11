package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.Reservation;
import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.entity.enums.RoomTypeEnum;
import bg.softuni.hotelagency.model.entity.enums.StarEnum;
import bg.softuni.hotelagency.repository.HotelRepository;
import bg.softuni.hotelagency.repository.ReservationRepository;
import bg.softuni.hotelagency.repository.RoomRepository;
import bg.softuni.hotelagency.repository.UserRepository;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user1 = new User();
        user1
                .setEmail("ivan@abv.bg")
                .setFirstName("ivan")
                .setLastName("ivanov")
                .setPassword("testpass")
                .setProfilePicture("https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png")
                .setPhoneNumber("0888888");
        userRepository.save(user1);

        Hotel hotel = new Hotel();
        hotel.setName("TestHotel")
                .setOwner(user1)
                .setStars(StarEnum.FIVE)
                .setEmail("hotel@emial")
                .setAddress("Sofia")
                .setDescription("desc....");
hotelRepository.save(hotel);
        Room room = new Room();
        room.
                setHotel(hotel).
                setCount(3).
                setName("Clean Room").
                setPrice(BigDecimal.TEN).
                setType(RoomTypeEnum.DOUBLE).
                setSingleBedsCount(1).
                setTwinBedsCount(1);
roomRepository.save(room);
        Reservation reservation = new Reservation();
        reservation.
                setUser(user1).
                setRoom(room).
                setArriveDate(LocalDate.of(2021,5,5)).
                setLeaveDate(LocalDate.of(2021,5,6)).
                setCountOfRooms(2);
        reservationRepository.save(reservation);
    }

    @AfterEach
    public void cleanUp() {
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
        hotelRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "ivan@abv.bg")
    public void testReservationsGet() throws Exception {
        mockMvc.perform(get("/users/reservations")).
                andExpect(status().isOk()).
                andExpect(view().name("user-reservations")).
                andExpect(model().attributeExists("reservations"));
    }

}
