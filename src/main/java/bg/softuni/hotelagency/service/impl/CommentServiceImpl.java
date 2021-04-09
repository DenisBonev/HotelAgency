package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Comment;
import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.exception.EntityNotFoundException;
import bg.softuni.hotelagency.model.service.CommentServiceModel;
import bg.softuni.hotelagency.repository.CommentRepository;
import bg.softuni.hotelagency.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addComment(CommentServiceModel commentServiceModel) {
        commentRepository.save(modelMapper.map(commentServiceModel, Comment.class));
    }

    @Override
    public List<CommentServiceModel> getCommentsByHotel(Hotel hotel) {
        return commentRepository.
                getCommentsByHotel(hotel).
                stream().
                map(c -> modelMapper.map(c, CommentServiceModel.class)).
                collect(Collectors.toList());
    }
}
