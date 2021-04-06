package bg.softuni.hotelagency.model.service;

import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.entity.User;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class ReservationServiceModel {
    private Long id;
    private LocalDate arriveDate;
    private LocalDate leaveDate;
    private Integer countOfRooms;
    private Room room;
    private User user;

    public ReservationServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public ReservationServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getArriveDate() {
        return arriveDate;
    }

    public ReservationServiceModel setArriveDate(LocalDate arriveDate) {
        this.arriveDate = arriveDate;
        return this;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public ReservationServiceModel setLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
        return this;
    }

    public Integer getCountOfRooms() {
        return countOfRooms;
    }

    public ReservationServiceModel setCountOfRooms(Integer countOfRooms) {
        this.countOfRooms = countOfRooms;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public ReservationServiceModel setRoom(Room room) {
        this.room = room;
        return this;
    }

    public User getUser() {
        return user;
    }

    public ReservationServiceModel setUser(User user) {
        this.user = user;
        return this;
    }
}
