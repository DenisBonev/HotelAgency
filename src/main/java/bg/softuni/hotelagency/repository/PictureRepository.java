package bg.softuni.hotelagency.repository;

import bg.softuni.hotelagency.model.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    @Query("SELECT p.url FROM Picture p WHERE p.hotel.id=?1")
    List<String> getPicturesByHotelId(Long id);
}
