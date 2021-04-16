package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Comment;
import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.entity.UserRole;
import bg.softuni.hotelagency.model.entity.enums.RoleEnum;
import bg.softuni.hotelagency.model.service.CommentServiceModel;
import bg.softuni.hotelagency.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    private CommentServiceImpl serviceToTest;
    private UserRole roleUser;
    private User mockUser;
    private Hotel mockHotel;

    @Mock
    CommentRepository mockCommentRepository;

    @BeforeEach
    public void setUp() {
        roleUser = new UserRole();
        roleUser.setName(RoleEnum.USER);
        UserRole roleOwner = new UserRole();
        roleOwner.setName(RoleEnum.HOTEL_OWNER);

        mockUser = new User();
        mockUser.setEmail("test@test");
        mockUser.setFirstName("Peter");
        mockUser.setLastName("Petrov");
        mockUser.setRoles(List.of(roleUser, roleOwner));

        mockHotel = new Hotel();
        mockHotel.setName("testHotel");
        mockHotel.setEmail("testHotel");
        mockHotel.setOwner(mockUser);

        serviceToTest = new CommentServiceImpl(mockCommentRepository, new ModelMapper());
    }

    @Test
    public void testGetCommentsByHotel() {
        UserRole roleUser = new UserRole();
        roleUser.setName(RoleEnum.USER);
        UserRole roleOwner = new UserRole();
        roleOwner.setName(RoleEnum.HOTEL_OWNER);

        User mockUser = new User();
        mockUser.setEmail("test@test");
        mockUser.setFirstName("Peter");
        mockUser.setLastName("Petrov");
        mockUser.setRoles(List.of(roleUser, roleOwner));

        Hotel mockHotel = new Hotel();
        mockHotel.setName("testHotel");
        mockHotel.setEmail("testHotel");
        mockHotel.setOwner(mockUser);

        Comment mockComment = new Comment();
        mockComment.setHotel(mockHotel);
        mockComment.setContent("Testing...");
        mockComment.setUser(mockUser);

        when(mockCommentRepository.getCommentsByHotel(mockHotel)).
                thenReturn(List.of(mockComment));
        List<CommentServiceModel> commentServiceModels = serviceToTest.getCommentsByHotel(mockHotel);

        assertEquals(1, commentServiceModels.size());
        assertEquals(mockComment.getHotel(), commentServiceModels.get(0).getHotel());
        assertEquals(mockComment.getContent(), commentServiceModels.get(0).getContent());
        assertEquals(mockComment.getUser().getEmail(), commentServiceModels.get(0).getUser().getEmail());
    }
}
