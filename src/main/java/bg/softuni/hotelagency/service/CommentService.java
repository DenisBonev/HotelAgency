package bg.softuni.hotelagency.service;

import bg.softuni.hotelagency.model.entity.Comment;
import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.service.CommentServiceModel;

import java.util.List;

public interface CommentService {
    void addComment(CommentServiceModel commentServiceModel);

    List<CommentServiceModel> getCommentsByHotel(Hotel hotel);
}
