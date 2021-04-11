package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.UserRole;
import bg.softuni.hotelagency.model.entity.enums.RoleEnum;
import bg.softuni.hotelagency.repository.UserRoleRepository;
import bg.softuni.hotelagency.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void populateRoles() {
        if (userRoleRepository.count() == 0) {
            Arrays.stream(RoleEnum.values()).forEach(ur->{
                UserRole userRole = new UserRole();
                userRole.setName(ur);
                userRoleRepository.save(userRole);
            });
        }
    }
}
