package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.Reservation;
import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.entity.enums.RoleEnum;
import bg.softuni.hotelagency.model.entity.enums.RoomTypeEnum;
import bg.softuni.hotelagency.model.entity.enums.StarEnum;
import bg.softuni.hotelagency.model.exception.EntityNotFoundException;
import bg.softuni.hotelagency.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    private User user1;

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    LogRepository logRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        user1 = new User();
        user1
                .setEmail("ivan@abv.bg")
                .setFirstName("ivan")
                .setLastName("ivanov")
                .setPassword("testpass")
                .setProfilePicture("https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png")
                .setPhoneNumber("0888888888");
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
                setArriveDate(LocalDate.of(2021, 5, 5)).
                setLeaveDate(LocalDate.of(2021, 5, 6)).
                setCountOfRooms(2);
        reservationRepository.save(reservation);
    }

    @AfterEach
    public void cleanUp() {
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
        hotelRepository.deleteAll();
        logRepository.deleteAll();
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

    @Test
    public void testRegisterUserGet() throws Exception {
        mockMvc.perform(get("/users/register")).
                andExpect(status().isOk()).
                andExpect(view().name("register-user")).
                andExpect(model().attributeExists("usernameOccupied"));
    }

    @Test
    public void testLoginGet() throws Exception {
        mockMvc.perform(get("/users/login")).
                andExpect(status().isOk()).
                andExpect(view().name("login"));

    }

    @Test
    @WithMockUser(username = "ivan@abv.bg")
    public void testUserProfileGet() throws Exception {
        mockMvc.perform(get("/users/" + userRepository.findUserByEmail("ivan@abv.bg").
                orElseThrow(() -> new EntityNotFoundException("User")).getId())).
                andExpect(status().isOk()).
                andExpect(view().name("user-profile")).
                andExpect(model().attributeExists("user", "isOwner"));
    }

    @Test
    @WithMockUser(username = "ivan@abv.bg")
    public void testMyProfileGet() throws Exception {
        mockMvc.perform(get("/users/my-profile")).
                andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "ivan@abv.bg")
    public void testEditProfileGet() throws Exception {
        mockMvc.perform(get("/users/edit-profile")).
                andExpect(status().isOk()).
                andExpect(view().name("edit-user")).
                andExpect(model().attributeExists("usernameOccupied", "userData"));
    }

    @WithMockUser(username = "ivan@abv.bg", roles = {"USER", "ADMIN"})
    @Test
    public void testEditProfilePatch() throws Exception {
        mockMvc.perform(patch("/users/edit-profile").
                param("firstName", "Changed").
                param("lastName", "Changed").
                param("email", "changed@mail.com").
                param("phoneNumber", "0888888888")
                .with(csrf())).
                andExpect(status().is3xxRedirection());

        assertEquals("changed@mail.com", userRepository.findById(user1.getId())
                .orElseThrow(() -> new EntityNotFoundException("User")).getEmail());
        assertEquals("Changed", userRepository.findById(user1.getId())
                .orElseThrow(() -> new EntityNotFoundException("User")).getFirstName());
        assertEquals("Changed", userRepository.findById(user1.getId())
                .orElseThrow(() -> new EntityNotFoundException("User")).getLastName());
        assertEquals("0888888888", userRepository.findById(user1.getId())
                .orElseThrow(() -> new EntityNotFoundException("User")).getPhoneNumber());
    }

    @WithMockUser(username = "ivan@abv.bg", roles = {"USER", "ADMIN"})
    @Test
    public void testEditProfilePatchBindError() throws Exception {
        mockMvc.perform(patch("/users/edit-profile").
                param("firstName", "C").
                param("lastName", "Changed").
                param("email", "changed@mail.com").
                param("phoneNumber", "0888888888")
                .with(csrf())).
                andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.userEditBindingModel"))
                .andExpect(flash().attributeExists("userEditBindingModel"));
    }

    @WithMockUser(username = "ivan@abv.bg", roles = {"USER", "ADMIN"})
    @Test
    public void testEditProfilePatchExistingEmail() throws Exception {
        userRepository.save(new User().
                setEmail("peter@abv.bg").
                setFirstName("Peter").
                setLastName("Petrov").
                setPhoneNumber("09090909").
                setProfilePicture("http://fakepic").
                setPassword("mockpass"));
        mockMvc.perform(patch("/users/edit-profile").
                param("firstName", "Changed").
                param("lastName", "Changed").
                param("email", "peter@abv.bg").
                param("phoneNumber", "0888888888")
                .with(csrf())).
                andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("usernameOccupied"))
                .andExpect(flash().attributeExists("userEditBindingModel"));
    }

    @Test
    @WithMockUser(username = "ivan@abv.bg", roles = {"USER", "ADMIN"})
    public void testChangeRoles() throws Exception {
        mockMvc.perform(patch("/users/change-roles/" + user1.getId()).
                param("admin", String.valueOf(RoleEnum.ADMIN)).
                param("user", String.valueOf(RoleEnum.USER)).
                param("hotelOwner", String.valueOf(RoleEnum.HOTEL_OWNER)).
                with(csrf())).
                andExpect(status().is3xxRedirection());

        assertEquals(3, userRepository.findById(user1.getId())
                .orElseThrow(() -> new EntityNotFoundException("User")).getRoles().size());
    }

    @Test
    public void testRegisterUserPost() throws Exception {
        mockMvc.perform(post("/users/register").
                param("firstName", "Register").
                param("lastName", "Test").
                param("email", "peter@abv.bg").
                param("birthDate", "2000-03-03").
                param("phoneNumber", "0808080808").
                param("password", "mockpass").
                param("confirmPassword", "mockpass").
                with(csrf())).
                andExpect(status().is3xxRedirection());

        assertEquals(2, userRepository.count());
    }
    @Test
    public void testRegisterUserPostBindError() throws Exception {
        mockMvc.perform(post("/users/register").
                param("firstName", "R").
                param("lastName", "Test").
                param("email", "peter@abv.bg").
                param("birthDate", "2000-03-03").
                param("phoneNumber", "0808080808").
                param("password", "mockpass").
                param("confirmPassword", "mockpass").
                with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(flash().attributeExists("userRegisterBindingModel")).
                andExpect(flash().attributeExists("org.springframework.validation.BindingResult.userRegisterBindingModel"));

        assertEquals(1, userRepository.count());
    }
    @Test
    public void testRegisterUserPostEmailExists() throws Exception {
        mockMvc.perform(post("/users/register").
                param("firstName", "Register").
                param("lastName", "Test").
                param("email", "ivan@abv.bg").
                param("birthDate", "2000-03-03").
                param("phoneNumber", "0808080808").
                param("password", "mockpass").
                param("confirmPassword", "mockpass").
                with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(flash().attributeExists("userRegisterBindingModel")).
                andExpect(flash().attributeExists("usernameOccupied"));

        assertEquals(1, userRepository.count());
    }
}
