package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.Constants;
import bg.softuni.hotelagency.model.view.UserRoleViewModel;
import bg.softuni.hotelagency.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("/profile-pic")
    public ResponseEntity<String> getProfilePicture(@AuthenticationPrincipal UserDetails principal) {
        String profilePic = userService.getUserByEmail(principal.getUsername()).getProfilePicture();
        return ResponseEntity.ok().body(profilePic);
    }
}
