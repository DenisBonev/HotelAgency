package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.entity.Comment;
import bg.softuni.hotelagency.model.service.CommentServiceModel;
import bg.softuni.hotelagency.model.view.CommentViewModel;
import bg.softuni.hotelagency.service.CommentService;
import bg.softuni.hotelagency.service.HotelService;
import bg.softuni.hotelagency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentRestController {

    private final CommentService commentService;
    private final HotelService hotelService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public CommentRestController(CommentService commentService, HotelService hotelService, UserService userService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.hotelService = hotelService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/hotels/{hotelId}/add-comment")
    public ResponseEntity<Comment> addComment(@PathVariable Long hotelId,
                                              @AuthenticationPrincipal UserDetails principal,
                                              @RequestBody String content) {
        if (content.length() == 0) {
            return ResponseEntity.badRequest().build();
        }
        CommentServiceModel commentServiceModel = new CommentServiceModel();
        commentServiceModel.
                setContent(content).
                setHotel(hotelService.getHotelById(hotelId)).
                setUser(userService.getUserByEmail(principal.getUsername())).
                setPostedOn(LocalDate.now());

        commentService.addComment(commentServiceModel);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/hotels/{hotelId}/comments")
    public ResponseEntity<List<CommentViewModel>> getAllComments(@PathVariable Long hotelId) {
        List<CommentViewModel> comments = commentService.
                getCommentsByHotel(hotelService.getHotelById(hotelId)).
                stream().
                map(c -> {
                    CommentViewModel commentViewModel = modelMapper.map(c, CommentViewModel.class);
                    commentViewModel.setUserNames(c.getUser().getFirstName() + " " + c.getUser().getLastName());
                    commentViewModel.setUserPic(c.getUser().getProfilePicture());
                    commentViewModel.setPostedOn(formatPostedOn(c.getPostedOn()));
                    return commentViewModel;
                }).collect(Collectors.toList());
        return ResponseEntity.ok().body(comments);
    }

    private String formatPostedOn(LocalDate postedOn) {
        int days = Period.between(postedOn,LocalDate.now()).getDays();
        if (days == 0) {
            return "Today";
        }
        return String.format("%d days ago", days);
    }
}
