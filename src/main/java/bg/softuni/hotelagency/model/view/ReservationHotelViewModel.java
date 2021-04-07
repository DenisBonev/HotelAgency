package bg.softuni.hotelagency.model.view;

import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.entity.User;

import java.time.LocalDate;

public class ReservationHotelViewModel {
    private Long id;
    private LocalDate arriveDate;
    private LocalDate leaveDate;
    private Integer countOfRooms;
    private Room room;
    private User user;

    public ReservationHotelViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ReservationHotelViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getArriveDate() {
        return arriveDate;
    }

    public ReservationHotelViewModel setArriveDate(LocalDate arriveDate) {
        this.arriveDate = arriveDate;
        return this;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public ReservationHotelViewModel setLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
        return this;
    }

    public Integer getCountOfRooms() {
        return countOfRooms;
    }

    public ReservationHotelViewModel setCountOfRooms(Integer countOfRooms) {
        this.countOfRooms = countOfRooms;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public ReservationHotelViewModel setRoom(Room room) {
        this.room = room;
        return this;
    }

    public User getUser() {
        return user;
    }

    public ReservationHotelViewModel setUser(User user) {
        this.user = user;
        return this;
    }
}
