package bg.softuni.hotelagency.event;

import bg.softuni.hotelagency.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationEventTest {

    private ReservationEvent reservationEvent;
    @Mock
    ReservationService reservationService;

    @BeforeEach
    public void setUp(){
        reservationEvent=new ReservationEvent(reservationService);
    }

    @Test
    public void testCleanReservations(){
        reservationEvent.cleanReservations();

        Mockito.verify(reservationService).deletePastReservations();
    }
}
