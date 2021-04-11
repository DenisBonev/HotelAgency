package bg.softuni.hotelagency.event;

import bg.softuni.hotelagency.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
public class ReservationEvent {

    private final Logger LOGGER = LoggerFactory.getLogger(ReservationEvent.class);
    private final ReservationService reservationService;

    public ReservationEvent(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Async
    @Transactional
    @Scheduled(cron = "0 0 0 */1 * *")
    public void cleanReservations(){
        reservationService.deletePastReservations();
        LOGGER.info("Out of date reservations cleaned");
    }
}
