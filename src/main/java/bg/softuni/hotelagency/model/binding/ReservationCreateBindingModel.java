package bg.softuni.hotelagency.model.binding;

import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.validation.ReservationDates;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@ReservationDates(first = "arriveDate",second = "leaveDate")
public class ReservationCreateBindingModel {
    @NotNull(message = "Date must be chosen")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate arriveDate;
    @NotNull(message = "Date must be chosen")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate leaveDate;
    @Positive(message = "At least one room should be reserved")
    @NotNull
    private Integer countOfRooms;
    @NotNull
    private Long room;

    public ReservationCreateBindingModel() {
    }

    public LocalDate getArriveDate() {
        return arriveDate;
    }

    public ReservationCreateBindingModel setArriveDate(LocalDate arriveDate) {
        this.arriveDate = arriveDate;
        return this;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public ReservationCreateBindingModel setLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
        return this;
    }

    public Integer getCountOfRooms() {
        return countOfRooms;
    }

    public ReservationCreateBindingModel setCountOfRooms(Integer countOfRooms) {
        this.countOfRooms = countOfRooms;
        return this;
    }

    public Long getRoom() {
        return room;
    }

    public ReservationCreateBindingModel setRoom(Long room) {
        this.room = room;
        return this;
    }
}
