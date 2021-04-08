package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.view.UserRoleViewModel;
import bg.softuni.hotelagency.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserRoleViewModel>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }
}
