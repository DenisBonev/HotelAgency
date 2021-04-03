package bg.softuni.hotelagency.service;


import bg.softuni.hotelagency.model.entity.Reservation;
import bg.softuni.hotelagency.model.service.ReservationServiceModel;

public interface ReservationService {
    Reservation addReservation(ReservationServiceModel reservationServiceModel);
}
