package bg.softuni.hotelagency.service;

import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.entity.enums.RoleEnum;
import bg.softuni.hotelagency.model.service.ReservationServiceModel;
import bg.softuni.hotelagency.model.service.UserServiceModel;
import bg.softuni.hotelagency.model.view.UserRoleViewModel;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void populateInitialUsers();

    Long registerUser(UserServiceModel userServiceModel) throws IOException;

    boolean usernameExists(String email);

    User getUserByEmail(String username);

    List<ReservationServiceModel> getUserReservationsByEmail(String email);

    void updateUser(UserServiceModel userServiceModel) throws IOException;

    User getUserById(Long id);

    List<UserRoleViewModel> getAllUsers();

    void setUserRoles(Long userId, List<RoleEnum> roles);
}
