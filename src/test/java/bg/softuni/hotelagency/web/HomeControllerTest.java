package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin@abv.bg")
    public void testGetHomeLogged() throws Exception {
        mockMvc.perform(get("/")).
                andExpect(status().isOk()).
                andExpect(view().name("home"));
    }

    @Test
    public void testGetHomeNotLogged() throws Exception {
        mockMvc.perform(get("/")).
                andExpect(status().isOk()).
                andExpect(view().name("index"));
    }
}
