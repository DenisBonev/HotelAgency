package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.entity.UserRole;
import bg.softuni.hotelagency.model.entity.enums.RoleEnum;
import bg.softuni.hotelagency.model.exception.EntityNotFoundException;
import bg.softuni.hotelagency.model.service.ReservationServiceModel;
import bg.softuni.hotelagency.model.service.UserServiceModel;
import bg.softuni.hotelagency.model.view.UserRoleViewModel;
import bg.softuni.hotelagency.repository.UserRepository;
import bg.softuni.hotelagency.repository.UserRoleRepository;
import bg.softuni.hotelagency.service.CloudinaryService;
import bg.softuni.hotelagency.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserServiceImpl serviceToTest;

    User user;
    UserRole userRole;

    @Mock
    UserRepository userRepository;
    @Mock
    UserRoleRepository userRoleRepository;
    @Mock
    CloudinaryService cloudinaryService;
    @Mock
    ReservationService reservationService;
    ModelMapper modelMapper = new ModelMapper();
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setUp() {
        userRole = new UserRole();
               userRole.setName(RoleEnum.USER)
                .setId(1L);

        user = new User();
        user.
                setEmail("user@mail.bg").
                setFirstName("Test").
                setLastName("Petrov").
                setRoles(List.of(userRole)).
                setId(1L);

        serviceToTest = new UserServiceImpl(userRepository, userRoleRepository, passwordEncoder, cloudinaryService, modelMapper, reservationService);
    }

    @Test
    public void testUsernameExists() {
        when(userRepository.findUserByEmail(user.getEmail())).
                thenReturn(Optional.of(user));

        assertTrue(serviceToTest.usernameExists(user.getEmail()));
    }

    @Test
    public void testUsernameNotExists() {
        String fakeEmail = "FakeEmail";
        when(userRepository.findUserByEmail(fakeEmail)).
                thenReturn(Optional.empty());

        assertFalse(serviceToTest.usernameExists(fakeEmail));
    }

    @Test
    public void testGetUserByEmailThrow() {
        String fakeEmail = "FakeEmail";
        when(userRepository.findUserByEmail(fakeEmail)).
                thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serviceToTest.getUserByEmail(fakeEmail));
    }

    @Test
    public void testGetUserByEmail() {
        when(userRepository.findUserByEmail(user.getEmail())).
                thenReturn(Optional.of(user));

        User userFound = serviceToTest.getUserByEmail(user.getEmail());
        assertEquals(user.getId(), userFound.getId());
        assertEquals(user.getEmail(), userFound.getEmail());
        assertEquals(user.getFirstName(), userFound.getFirstName());
    }

    @Test
    public void testGetUserReservationsByEmail() {
        ReservationServiceModel reservation1 = new ReservationServiceModel();
        reservation1.setUser(user).setId(1L);
        ReservationServiceModel reservation2 = new ReservationServiceModel();
        reservation2.setUser(user).setId(2L);
        when(reservationService.getReservationsByUser(user)).
                thenReturn(List.of(reservation1, reservation2));
        when(userRepository.findUserByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        List<ReservationServiceModel> reservationServiceModels = serviceToTest.getUserReservationsByEmail(user.getEmail());

        assertEquals(2, reservationServiceModels.size());
        assertEquals(reservation1.getUser().getId(), reservationServiceModels.get(0).getUser().getId());
        assertEquals(reservation2.getUser().getId(), reservationServiceModels.get(1).getUser().getId());
        assertEquals(reservation1.getId(), reservationServiceModels.get(0).getId());
        assertEquals(reservation2.getId(), reservationServiceModels.get(1).getId());
    }

    @Test
    public void testGetUserReservationsByEmailThrow() {
        String fakeEmail = "fakeEmail";
        when(userRepository.findUserByEmail(fakeEmail))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serviceToTest.getUserReservationsByEmail(fakeEmail));
    }


    @Test
    public void testUpdateUser() throws IOException {
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel
                .setId(user.getId())
                .setEmail("updatedEmail")
                .setFirstName("Update")
                .setLastName("Georgiev")
                .setPhoneNumber("000000")
                .setProfilePicture(new MockMultipartFile("profilePic", new byte[1]));

        when(userRepository.findById(userServiceModel.getId()))
                .thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        serviceToTest.updateUser(userServiceModel);

        assertEquals(userServiceModel.getEmail(), user.getEmail());
        assertEquals(userServiceModel.getId(), user.getId());
        assertEquals(userServiceModel.getFirstName(), user.getFirstName());
        assertEquals(userServiceModel.getLastName(), user.getLastName());
        assertEquals(userServiceModel.getPhoneNumber(), user.getPhoneNumber());
    }

    @Test
    public void testUpdateUserWithProfilePic() throws IOException {
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel
                .setId(user.getId())
                .setEmail("updatedEmail")
                .setFirstName("Update")
                .setLastName("Georgiev")
                .setPhoneNumber("000000")
                .setProfilePicture(new MockMultipartFile("profilePic", "test", "png", new byte[1]));

        when(userRepository.findById(userServiceModel.getId()))
                .thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(cloudinaryService.uploadImage(userServiceModel.getProfilePicture())).thenReturn("profilePic");

        serviceToTest.updateUser(userServiceModel);

        assertEquals(userServiceModel.getEmail(), user.getEmail());
        assertEquals(userServiceModel.getId(), user.getId());
        assertEquals(userServiceModel.getFirstName(), user.getFirstName());
        assertEquals(userServiceModel.getLastName(), user.getLastName());
        assertEquals(userServiceModel.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(userServiceModel.getProfilePicture().getName(), user.getProfilePicture());
    }

    @Test
    public void testUpdateUserThrow() throws IOException {
        Long fakeId = 1111L;
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel
                .setId(fakeId)
                .setEmail("updatedEmail")
                .setFirstName("Update")
                .setLastName("Georgiev")
                .setPhoneNumber("000000")
                .setProfilePicture(new MockMultipartFile("profilePic", new byte[1]));

        when(userRepository.findById(fakeId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serviceToTest.updateUser(userServiceModel));
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(user.getId())).
                thenReturn(Optional.of(user));

        User returned = serviceToTest.getUserById(user.getId());

        assertEquals(user.getId(), returned.getId());
        assertEquals(user.getEmail(), returned.getEmail());
        assertEquals(user.getFirstName(), returned.getFirstName());
    }

    @Test
    public void testGetUserByIdThrow() {
        long fakeId = 1111L;
        when(userRepository.findById(fakeId)).
                thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serviceToTest.getUserById(fakeId));
    }

    @Test
    public void testGetAllUsers() {
        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("second@email.com")
        .setRoles(List.of(userRole));
        when(userRepository.findAll())
                .thenReturn(List.of(user, user2));

        List<UserRoleViewModel> users = serviceToTest.getAllUsers();
        assertEquals(user.getEmail(),users.get(0).getEmail());
        assertEquals(user.getId(),users.get(0).getId());
        assertEquals(user2.getEmail(),users.get(1).getEmail());
        assertEquals(user2.getId(),users.get(1).getId());
    }
}

