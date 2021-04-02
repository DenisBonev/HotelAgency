package bg.softuni.hotelagency.repository;

import bg.softuni.hotelagency.model.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture,Long> {
}
