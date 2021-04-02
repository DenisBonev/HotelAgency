package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Reservation;
import bg.softuni.hotelagency.model.service.ReservationServiceModel;
import bg.softuni.hotelagency.repository.ReservationRepository;
import bg.softuni.hotelagency.service.ReservationService;
import bg.softuni.hotelagency.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
    public void addReservation(ReservationServiceModel reservationServiceModel) {
        LocalDate currDate = reservationServiceModel.getArriveDate();
        while (currDate.isBefore(reservationServiceModel.getLeaveDate()) || currDate.isEqual(reservationServiceModel.getLeaveDate())) {
            Integer reservedRoomsCount = reservationRepository.getReservedRoomsCountAtDate(currDate, reservationServiceModel.getRoom());
            Integer roomAllCount = roomService.getRoomsCountByRoom(reservationServiceModel.getRoom());
            if (roomAllCount-(reservedRoomsCount+reservationServiceModel.getCountOfRooms())<0){
                throw new IllegalStateException("rooms not enogh");
                //TODO:optimize free rooms check(via JPA) and return appropriate message
            }
        }
        reservationRepository.
                save(modelMapper.map(reservationServiceModel, Reservation.class));
    }
}
