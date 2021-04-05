package bg.softuni.hotelagency.service;

import bg.softuni.hotelagency.model.entity.Room;
import bg.softuni.hotelagency.model.service.RoomServiceModel;

import java.util.List;

public interface RoomService {
    Room createRoom(RoomServiceModel map);

    List<RoomServiceModel> getHotelsRooms(Long hotelId);

    Integer getRoomsCountByRoom(Room room);

    Room getRoomById(Long id);
}
