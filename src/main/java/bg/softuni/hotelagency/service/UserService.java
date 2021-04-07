package bg.softuni.hotelagency.service;

import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.service.ReservationServiceModel;
import bg.softuni.hotelagency.model.service.UserServiceModel;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void populateAdmin();

    void registerUser(UserServiceModel userServiceModel) throws IOException;

    boolean usernameExists(String email);

    User getUserByEmail(String username);

    List<ReservationServiceModel> getUserReservationsByEmail(String email);

    void updateUser(UserServiceModel userServiceModel) throws IOException;

    User getUserById(Long id);
}
