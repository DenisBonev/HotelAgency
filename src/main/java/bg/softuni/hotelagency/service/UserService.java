package bg.softuni.hotelagency.service;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.service.UserServiceModel;

import java.io.IOException;

public interface UserService {
    void populateAdmin();

    void registerUser(UserServiceModel userServiceModel) throws IOException;

    boolean usernameExists(String email);

    User getUserByEmail(String username);
}
