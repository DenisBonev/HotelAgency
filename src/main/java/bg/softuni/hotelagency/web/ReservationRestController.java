package bg.softuni.hotelagency.web;

import bg.softuni.hotelagency.model.entity.Reservation;
import bg.softuni.hotelagency.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationRestController {

    private final ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //TODO:make it work
    @DeleteMapping("/delete")
    public ResponseEntity<Reservation> deleteReservation(@RequestBody Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.status(204).build();
    }
}
