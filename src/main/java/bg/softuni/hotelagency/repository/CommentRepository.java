package bg.softuni.hotelagency.repository;

import bg.softuni.hotelagency.model.entity.Comment;
import bg.softuni.hotelagency.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> getCommentsByHotel(Hotel hotel);
}
