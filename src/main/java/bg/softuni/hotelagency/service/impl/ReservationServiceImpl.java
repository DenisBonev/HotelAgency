package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Reservation;
import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.exception.EntityNotFoundException;
import bg.softuni.hotelagency.model.service.ReservationServiceModel;
import bg.softuni.hotelagency.repository.ReservationRepository;
import bg.softuni.hotelagency.service.ReservationService;
import bg.softuni.hotelagency.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final ModelMapper modelMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, RoomService roomService, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Reservation addReservation(ReservationServiceModel reservationServiceModel) {
        LocalDate currDate = reservationServiceModel.getArriveDate();
        while (currDate.isBefore(reservationServiceModel.getLeaveDate()) || currDate.isEqual(reservationServiceModel.getLeaveDate())) {
            Integer reservedRoomsCount = reservationRepository.getReservedRoomsCountAtDate(currDate, reservationServiceModel.getRoom());
            Integer roomAllCount = roomService.getRoomsCountByRoom(reservationServiceModel.getRoom());
            if (roomAllCount - (reservedRoomsCount + reservationServiceModel.getCountOfRooms()) < 0) {
                return null;
            }
            currDate = currDate.plusDays(1);
        }
        return reservationRepository.
                save(modelMapper.map(reservationServiceModel, Reservation.class));
    }

    @Override
    public List<ReservationServiceModel> getReservationsByUser(User user) {
        return reservationRepository.getReservationsByUserOrderByArriveDate(user).stream().map(r -> modelMapper.map(r, ReservationServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.
                delete(reservationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Reservation")));
    }

    @Override
    public List<ReservationServiceModel> getReservationsByHotelId(Long id) {
        return reservationRepository.
                getReservationsByRoomHotelIdOrderByArriveDate(id).
                stream().
                map(r -> modelMapper.map(r, ReservationServiceModel.class)).
                collect(Collectors.toList());
    }

    @Override
    public void deletePastReservations() {
        reservationRepository.deleteReservationsByLeaveDateBefore(LocalDate.now());
    }
}
