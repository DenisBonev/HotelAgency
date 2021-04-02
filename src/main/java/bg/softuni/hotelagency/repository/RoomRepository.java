package bg.softuni.hotelagency.repository;

import bg.softuni.hotelagency.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> getRoomsByHotelIdOrderByPrice(Long id);

    @Query("SELECT r.count FROM Room r WHERE r=?1")
    Integer getRoomsCountByRoom(Room room);
}
