package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Hotel;
import bg.softuni.hotelagency.model.entity.Reservation;
import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.entity.enums.RoomTypeEnum;
import bg.softuni.hotelagency.model.exception.EntityNotFoundException;
import bg.softuni.hotelagency.model.service.ReservationServiceModel;
import bg.softuni.hotelagency.repository.ReservationRepository;
import bg.softuni.hotelagency.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    private ReservationServiceImpl serviceToTest;

    Room room3, room2, room1;
    Reservation reservationRoom1, reservationRoom2, reservationRoom3;
    Hotel hotel;
    User user;


    @Mock
    ReservationRepository reservationRepository;
    @Mock
    RoomService roomService;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("email.com");


        hotel = new Hotel();
        hotel.
                setName("testHotel").
                setEmail("hotel@abv.bg")
                .setId(1L);

        room1 = new Room();
        room1.
                setType(RoomTypeEnum.APARTMENT).
                setName("TestApartment").
                setPrice(BigDecimal.valueOf(100.00)).
                setCount(20).
                setHotel(hotel).
                setId(1L);

        room2 = new Room();
        room2.
                setType(RoomTypeEnum.DOUBLE).
                setName("TestApartment2").
                setPrice(BigDecimal.valueOf(130.00)).
                setCount(10).
                setHotel(hotel).
                setId(2L);

        room3 = new Room();
        room3.
                setType(RoomTypeEnum.APARTMENT).
                setName("TestApartment3").
                setPrice(BigDecimal.valueOf(109.00)).
                setCount(5).
                setHotel(hotel).
                setId(3L);


        reservationRoom1 = new Reservation();
        reservationRoom1.
                setUser(user).
                setArriveDate(LocalDate.of(2021, 6, 1)).
                setLeaveDate(LocalDate.of(2021, 6, 4))
                .setCountOfRooms(2)
                .setUser(user)
                .setRoom(room1);
        reservationRoom2 = new Reservation();
        reservationRoom2.
                setUser(user).
                setArriveDate(LocalDate.of(2021, 6, 1)).
                setLeaveDate(LocalDate.of(2021, 6, 4))
                .setCountOfRooms(3)
                .setRoom(room2);
        reservationRoom3 = new Reservation();
        reservationRoom3.
                setUser(user).
                setArriveDate(LocalDate.of(2021, 6, 1)).
                setLeaveDate(LocalDate.of(2021, 6, 4))
                .setCountOfRooms(4)
                .setRoom(room3);


        serviceToTest = new ReservationServiceImpl(reservationRepository, roomService, new ModelMapper());
    }

    @Test
    public void testGetReservationsByUser() {
        when(reservationRepository.getReservationsByUserOrderByArriveDate(user)).
                thenReturn(List.of(reservationRoom1, reservationRoom2, reservationRoom3));

        List<ReservationServiceModel> reservationServiceModels = serviceToTest.getReservationsByUser(user);

        assertEquals(3, reservationServiceModels.size());

        assertEquals(reservationRoom1.getCountOfRooms(), reservationServiceModels.get(0).getCountOfRooms());
        assertEquals(reservationRoom1.getRoom().getId(), reservationServiceModels.get(0).getRoom().getId());
        assertEquals(reservationRoom1.getUser().getEmail(), reservationServiceModels.get(0).getUser().getEmail());
        assertEquals(reservationRoom1.getArriveDate(), reservationServiceModels.get(0).getArriveDate());
        assertEquals(reservationRoom1.getLeaveDate(), reservationServiceModels.get(0).getLeaveDate());

        assertEquals(reservationRoom2.getCountOfRooms(), reservationServiceModels.get(1).getCountOfRooms());
        assertEquals(reservationRoom2.getRoom().getId(), reservationServiceModels.get(1).getRoom().getId());
        assertEquals(reservationRoom2.getUser().getEmail(), reservationServiceModels.get(1).getUser().getEmail());
        assertEquals(reservationRoom2.getArriveDate(), reservationServiceModels.get(1).getArriveDate());
        assertEquals(reservationRoom2.getLeaveDate(), reservationServiceModels.get(1).getLeaveDate());

        assertEquals(reservationRoom3.getCountOfRooms(), reservationServiceModels.get(2).getCountOfRooms());
        assertEquals(reservationRoom3.getRoom().getId(), reservationServiceModels.get(2).getRoom().getId());
        assertEquals(reservationRoom3.getUser().getEmail(), reservationServiceModels.get(2).getUser().getEmail());
        assertEquals(reservationRoom3.getArriveDate(), reservationServiceModels.get(2).getArriveDate());
        assertEquals(reservationRoom3.getLeaveDate(), reservationServiceModels.get(2).getLeaveDate());
    }

    @Test
    public void testGetReservationsByHotelId() {
        when(reservationRepository.getReservationsByRoomHotelIdOrderByArriveDate(hotel.getId())).
                thenReturn(List.of(reservationRoom1, reservationRoom2, reservationRoom3));

        List<ReservationServiceModel> reservationServiceModels = serviceToTest.getReservationsByHotelId(hotel.getId());

        assertEquals(3, reservationServiceModels.size());
        assertEquals(hotel.getId(), reservationServiceModels.get(0).getRoom().getHotel().getId());
        assertEquals(hotel.getId(), reservationServiceModels.get(1).getRoom().getHotel().getId());
        assertEquals(hotel.getId(), reservationServiceModels.get(2).getRoom().getHotel().getId());

        assertEquals(reservationRoom1.getCountOfRooms(), reservationServiceModels.get(0).getCountOfRooms());
        assertEquals(reservationRoom1.getRoom().getId(), reservationServiceModels.get(0).getRoom().getId());
        assertEquals(reservationRoom1.getUser().getEmail(), reservationServiceModels.get(0).getUser().getEmail());
        assertEquals(reservationRoom1.getArriveDate(), reservationServiceModels.get(0).getArriveDate());
        assertEquals(reservationRoom1.getLeaveDate(), reservationServiceModels.get(0).getLeaveDate());

        assertEquals(reservationRoom2.getCountOfRooms(), reservationServiceModels.get(1).getCountOfRooms());
        assertEquals(reservationRoom2.getRoom().getId(), reservationServiceModels.get(1).getRoom().getId());
        assertEquals(reservationRoom2.getUser().getEmail(), reservationServiceModels.get(1).getUser().getEmail());
        assertEquals(reservationRoom2.getArriveDate(), reservationServiceModels.get(1).getArriveDate());
        assertEquals(reservationRoom2.getLeaveDate(), reservationServiceModels.get(1).getLeaveDate());

        assertEquals(reservationRoom3.getCountOfRooms(), reservationServiceModels.get(2).getCountOfRooms());
        assertEquals(reservationRoom3.getRoom().getId(), reservationServiceModels.get(2).getRoom().getId());
        assertEquals(reservationRoom3.getUser().getEmail(), reservationServiceModels.get(2).getUser().getEmail());
        assertEquals(reservationRoom3.getArriveDate(), reservationServiceModels.get(2).getArriveDate());
        assertEquals(reservationRoom3.getLeaveDate(), reservationServiceModels.get(2).getLeaveDate());
    }

    @Test
    public void testDeleteReservation(){
        when(reservationRepository.findById(reservationRoom1.getId()))
                .thenReturn(Optional.of(reservationRoom1));

        serviceToTest.deleteReservation(reservationRoom1.getId());

        Mockito.verify(reservationRepository).delete(reservationRoom1);
    }
    @Test
    public void testDeleteReservationFail(){
        when(reservationRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()->serviceToTest.deleteReservation(999L));
    }

}
