package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.repository.UserRepository;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        User user1 = new User();
        user1
                .setEmail("ivan@abv.bg")
                .setFirstName("ivan")
                .setLastName("ivanov")
                .setPassword("testpass")
                .setProfilePicture("https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png")
                .setPhoneNumber("0888888");
        User user2 = new User();
        user2.setEmail("peter@abv.bg")
                .setFirstName("peter")
                .setLastName("petrov")
                .setPassword("testpass")
                .setProfilePicture("https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png")
                .setPhoneNumber("0888886");
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @AfterEach
    public void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "ivan@abv.bg")
    public void testGetProfilePic() throws Exception {
        mockMvc.perform(get("/users/profile-pic")).
                andExpect(status().isOk())
        .andExpect(jsonPath("$").value("https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png"));
    }

    @Test
    @WithMockUser(username = "ivan@abv.bg",roles = {"USER","ADMIN"})
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users/all")).
                andExpect(status().isOk()).
                andExpect(jsonPath("[0].email").value("ivan@abv.bg")).
                andExpect(jsonPath("[1].email").value("peter@abv.bg"));
    }

}
