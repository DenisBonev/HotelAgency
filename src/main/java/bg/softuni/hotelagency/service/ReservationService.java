package bg.softuni.hotelagency.service;


import bg.softuni.hotelagency.model.entity.Reservation;
import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.service.ReservationServiceModel;

import java.util.List;

public interface ReservationService {
    Reservation addReservation(ReservationServiceModel reservationServiceModel);

    List<ReservationServiceModel> getReservationsByUser(User user);

    void deleteReservation(Long id);
}
