package bg.softuni.hotelagency.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @GetMapping("/stats")
    public String adminMenu() {
        return "stats";
    }

    @GetMapping("/manage-users")
    public String manageUsers() {
        return "manage-users";
    }

}
