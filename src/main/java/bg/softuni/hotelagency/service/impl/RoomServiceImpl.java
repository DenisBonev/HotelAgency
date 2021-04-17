package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.exception.EntityNotFoundException;
import bg.softuni.hotelagency.model.service.RoomServiceModel;
import bg.softuni.hotelagency.repository.RoomRepository;
import bg.softuni.hotelagency.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    public RoomServiceImpl(RoomRepository roomRepository, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Room createRoom(RoomServiceModel roomServiceModel) {
        return roomRepository.save(modelMapper.map(roomServiceModel, Room.class));

    }

    @Override
    public List<RoomServiceModel> getHotelsRooms(Long hotelId) {
        return roomRepository.getRoomsByHotelIdOrderByPrice(hotelId).
                stream().
                map(r -> modelMapper.map(r, RoomServiceModel.class)).
                collect(Collectors.toList());
    }

    @Override
    public Integer getRoomsCountByRoom(Room room) {
        return roomRepository.getRoomsCountByRoom(room);
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Room"));
    }

    @Override
    public Long patchChanges(RoomServiceModel roomServiceModel) {
        Room room = roomRepository.findById(roomServiceModel.getId()).
                orElseThrow(() -> new EntityNotFoundException("Room"));
        roomServiceModel.setHotel(room.getHotel());
        modelMapper.map(roomServiceModel, room);
        roomRepository.save(room);
        return room.getHotel().getId();
    }
}
