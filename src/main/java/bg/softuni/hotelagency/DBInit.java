package bg.softuni.hotelagency;

import bg.softuni.hotelagency.service.UserRoleService;
import bg.softuni.hotelagency.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    private final UserService userService;
    private final UserRoleService userRoleService;

    public DBInit(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public void run(String... args) throws Exception {
        userRoleService.populateRoles();
        userService.populateAdmin();
    }
}
