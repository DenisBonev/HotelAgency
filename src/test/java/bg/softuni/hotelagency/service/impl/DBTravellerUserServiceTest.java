package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.entity.UserRole;
import bg.softuni.hotelagency.model.entity.enums.RoleEnum;
import bg.softuni.hotelagency.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class DBTravellerUserServiceTest {

    private DBTravellerUserService serviceToTest;

    @Mock
    UserRepository mockUserRepository;

    @BeforeEach
    public void setUp() {
        serviceToTest = new DBTravellerUserService(mockUserRepository);
    }
    @Test
    void testUserNotFound() {
        Assertions.assertThrows(
                UsernameNotFoundException.class, () -> {
                    serviceToTest.loadUserByUsername("user_does_not_exits");
                }
        );
    }

    @Test
    void testExistingUser() {
        // prepare data
        User userEntity = new User();
        userEntity.setEmail("denis@abv.bg");
        userEntity.setPassword("xyz");

        UserRole roleUser = new UserRole();
        roleUser.setName(RoleEnum.USER);
        UserRole roleAdmin = new UserRole();
        roleAdmin.setName(RoleEnum.ADMIN);

        userEntity.setRoles(List.of(roleUser, roleAdmin));

        // configure mocks
        Mockito.when(mockUserRepository.findUserByEmail("luchob")).
                thenReturn(Optional.of(userEntity));

        // test
        UserDetails userDetails = serviceToTest.loadUserByUsername("luchob");

        Assertions.assertEquals(userEntity.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());

        List<String> authorities = userDetails.
                getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                collect(Collectors.toList());

        Assertions.assertTrue(authorities.contains("ROLE_ADMIN"));
        Assertions.assertTrue(authorities.contains("ROLE_USER"));
    }
}
