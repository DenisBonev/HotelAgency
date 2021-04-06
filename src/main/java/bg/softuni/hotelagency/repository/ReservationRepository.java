package bg.softuni.hotelagency.repository;

import bg.softuni.hotelagency.model.entity.Reservation;
import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.service.ReservationServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT COALESCE(SUM(r.countOfRooms),0) FROM Reservation r WHERE ?1 BETWEEN r.arriveDate AND r.leaveDate AND r.room=?2")
    Integer getReservedRoomsCountAtDate(LocalDate date, Room room);

    List<Reservation> getReservationsByUserOrderByArriveDate(User user);
}
