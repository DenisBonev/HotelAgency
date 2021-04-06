package bg.softuni.hotelagency.model.view;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.Room;

import java.time.LocalDate;

public class ReservationTableViewModel {
    private Long id;
    private Hotel hotel;
    private LocalDate arriveDate;
    private LocalDate leaveDate;
    private Integer countOfRooms;
    private Room room;

    public ReservationTableViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ReservationTableViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public ReservationTableViewModel setHotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public LocalDate getArriveDate() {
        return arriveDate;
    }

    public ReservationTableViewModel setArriveDate(LocalDate arriveDate) {
        this.arriveDate = arriveDate;
        return this;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public ReservationTableViewModel setLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public ReservationTableViewModel setRoom(Room room) {
        this.room = room;
        return this;
    }

    public Integer getCountOfRooms() {
        return countOfRooms;
    }

    public ReservationTableViewModel setCountOfRooms(Integer countOfRooms) {
        this.countOfRooms = countOfRooms;
        return this;
    }
}
