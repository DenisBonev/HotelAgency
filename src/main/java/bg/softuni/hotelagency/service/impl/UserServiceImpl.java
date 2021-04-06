package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.entity.enums.RoleEnum;
import bg.softuni.hotelagency.model.exception.EntityNotFoundException;
import bg.softuni.hotelagency.model.service.ReservationServiceModel;
import bg.softuni.hotelagency.model.service.UserServiceModel;
import bg.softuni.hotelagency.repository.UserRepository;
import bg.softuni.hotelagency.repository.UserRoleRepository;
import bg.softuni.hotelagency.service.CloudinaryService;
import bg.softuni.hotelagency.service.ReservationService;
import bg.softuni.hotelagency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final ReservationService reservationService;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, CloudinaryService cloudinaryService, ModelMapper modelMapper, ReservationService reservationService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.reservationService = reservationService;
    }

    @Override
    public void populateAdmin() {
        if (userRepository.count() == 0) {
            User admin = new User().
                    setFirstName("Admin").
                    setLastName("Admin").
                    setEmail("admin@abv.bg").
                    setPassword(passwordEncoder.encode("topsecret")).
                    setRoles(List.of(
                            userRoleRepository.getUserRoleByName(RoleEnum.ADMIN).orElseThrow(() -> new EntityNotFoundException("UserRole")),
                            userRoleRepository.getUserRoleByName(RoleEnum.USER).orElseThrow(() -> new EntityNotFoundException("UserRole"))
                    ));
            userRepository.save(admin);
        }
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) throws IOException {
        User user = modelMapper.map(userServiceModel, User.class);
        if (!userServiceModel.getProfilePicture().isEmpty()) {
            user.setProfilePicture(cloudinaryService.
                    uploadImage(userServiceModel.
                            getProfilePicture()));
        } else {
            user.setProfilePicture(null);
        }
        user.setPassword(passwordEncoder.
                encode(userServiceModel.getPassword()));


        //TODO:Optimize user's role setting
        if (userServiceModel.isHotelOwner()) {
            user.setRoles(List.of(userRoleRepository.getUserRoleByName(RoleEnum.USER).orElseThrow(() -> new EntityNotFoundException("UserRole")),
                    userRoleRepository.getUserRoleByName(RoleEnum.HOTEL_OWNER).orElseThrow(() -> new EntityNotFoundException("UserRole"))));
        } else {
            user.setRoles(List.of(userRoleRepository.
                    getUserRoleByName(RoleEnum.USER).
                    orElseThrow(() -> new EntityNotFoundException("UserRole"))));
        }
        userRepository.save(user);
    }

    @Override
    public boolean usernameExists(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }


    @Override
    public User getUserByEmail(String username) {
        return userRepository.findUserByEmail(username).
                orElseThrow(() -> new EntityNotFoundException("User"));
    }

    @Override
    public List<ReservationServiceModel> getUserReservationsByEmail(String email) {
        return reservationService.getReservationsByUser(getUserByEmail(email));
    }

    @Override
    public void updateUser(UserServiceModel userServiceModel) throws IOException {
        User user = userRepository.findById(userServiceModel.getId()).
                orElseThrow(() -> new EntityNotFoundException("User"));
        user.
                setEmail(userServiceModel.getEmail()).
                setFirstName(userServiceModel.getFirstName()).
                setLastName(userServiceModel.getLastName()).
                setPhoneNumber(userServiceModel.getPhoneNumber());
        if (!"".equals(userServiceModel.getProfilePicture().getOriginalFilename())) {
            cloudinaryService.deleteByUrl(user.getProfilePicture());
            user.setProfilePicture(cloudinaryService.uploadImage(userServiceModel.getProfilePicture()));
        }
        userRepository.save(user);
    }
}
