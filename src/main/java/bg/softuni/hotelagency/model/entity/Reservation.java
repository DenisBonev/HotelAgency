package bg.softuni.hotelagency.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {
    @Column(nullable = false)
    private LocalDate arriveDate;
    @Column(nullable = false)
    private LocalDate leaveDate;
    @Column(nullable = false)
    private Integer countOfRooms;
    @ManyToOne
    private Room room;
    @ManyToOne
    private User user;

    public Reservation() {
    }

    public LocalDate getArriveDate() {
        return arriveDate;
    }

    public Reservation setArriveDate(LocalDate arriveDate) {
        this.arriveDate = arriveDate;
        return this;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public Reservation setLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
        return this;
    }

    public Integer getCountOfRooms() {
        return countOfRooms;
    }

    public Reservation setCountOfRooms(Integer countOfRooms) {
        this.countOfRooms = countOfRooms;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public Reservation setRoom(Room room) {
        this.room = room;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Reservation setUser(User user) {
        this.user = user;
        return this;
    }
}
